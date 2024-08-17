package ecnic.service.money.wallet.domain;

import static ecnic.service.money.wallet.domain.WalletMapper.convertToEntity;
import static ecnic.service.money.wallet.domain.WalletMapper.convertToWallet;
import static ecnic.service.money.wallet.domain.WalletMapper.convertToWallets;
import static ecnic.service.money.wallet.domain.WalletMapper.toEventMoveAmountWallet;
import static ecnic.service.money.wallet.domain.WalletMapper.updateEntity;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.transaction.domain.events.UpdateWalletBalanceEvent;
import ecnic.service.money.wallet.WalletService;
import ecnic.service.money.wallet.domain.events.MoveAmountWalletEvent;
import ecnic.service.money.wallet.domain.exceptions.WalletNotFoundException;
import ecnic.service.money.wallet.domain.exceptions.WalletRejectedMoveAmountException;
import ecnic.service.money.wallet.domain.models.CreateWallet;
import ecnic.service.money.wallet.domain.models.MoveAmountWallet;
import ecnic.service.money.wallet.domain.models.Wallet;
import ecnic.service.user.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

  private final WalletRepository walletRepository;
  private final UserService userService;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public PagedResult<Wallet> getAllWallets(Pageable pageable) {
    Page<Wallet> wallets = walletRepository.findAllBy(pageable);
    return new PagedResult<>(wallets);
  }

  @Override
  public Wallet getWallet(Long id) {

    Optional<WalletEntity> walletEntityOptional = walletRepository.findById(id);
    if (walletEntityOptional.isEmpty()) {
      throw WalletNotFoundException.forWalletId(id);
    }

    return convertToWallet(walletEntityOptional.get());
  }

  @Override
  public Wallet createWallet(CreateWallet createWallet) {
    WalletEntity entity = convertToEntity(createWallet);
    entity = walletRepository.saveAndFlush(entity);
    return convertToWallet(entity);
  }

  @Override
  public Wallet updateWallet(Long id, String newWalletName, BigDecimal newBalance) {
    Optional<WalletEntity> walletEntityOptional = walletRepository.findById(id);
    if (walletEntityOptional.isEmpty()) {
      throw WalletNotFoundException.forWalletId(id);
    }
    WalletEntity entity = walletEntityOptional.get();
    updateEntity(entity, newBalance, newWalletName);
    entity = walletRepository.saveAndFlush(walletEntityOptional.get());
    return convertToWallet(entity);
  }

  @Override
  public List<Wallet> moveAmount(MoveAmountWallet moveAmountWallet) {
    List<WalletEntity> walletEntities = walletRepository.findAllById(
        List.of(moveAmountWallet.walletIdReceive(), moveAmountWallet.walletIdSender()));

    validationNotFound(moveAmountWallet, walletEntities);

    Optional<WalletEntity> walletSender = walletEntities.stream()
        .filter(val -> val.getId().equals(moveAmountWallet.walletIdSender())).findFirst();
    Optional<WalletEntity> walletReceive = walletEntities.stream()
        .filter(val -> val.getId().equals(moveAmountWallet.walletIdReceive())).findFirst();
    if (walletSender.isPresent() && walletReceive.isPresent()) {
      validationAmountWalletSenderEnough(walletSender.get(), moveAmountWallet);

      // proceed reduce and addition
      BigDecimal reductionWalletBalance = walletSender.get().getBalance()
          .min(moveAmountWallet.amountSent());
      BigDecimal additionWalletBalance = walletReceive.get().getBalance()
          .add(moveAmountWallet.amountReceived());

      walletSender.get().setBalance(reductionWalletBalance);
      walletSender.get().setModifiedBy(moveAmountWallet.idUser().toString());
      walletReceive.get().setBalance(additionWalletBalance);
      walletSender.get().setModifiedBy(moveAmountWallet.idUser().toString());
      List<WalletEntity> entitiesTargetSave = List.of(walletSender.get(), walletReceive.get());
      walletRepository.saveAllAndFlush(entitiesTargetSave);

      triggerEventMoveAmount(walletSender.get(), walletReceive.get(), moveAmountWallet);
    }

    return convertToWallets(walletRepository.findAll());
  }

  private void triggerEventMoveAmount(WalletEntity walletIdSender, WalletEntity walletIdReceive,
      MoveAmountWallet moveAmountWallet) {
    // event to log transaction
    MoveAmountWalletEvent event = toEventMoveAmountWallet(walletIdSender, walletIdReceive,
        moveAmountWallet);
    eventPublisher.publishEvent(event);
  }


  private void validationAmountWalletSenderEnough(WalletEntity walletSender,
      MoveAmountWallet moveAmountWallet) {
    if (walletSender.getBalance().compareTo(moveAmountWallet.amountSent()) > 0) {
      log.debug("when amount not enough to move");
      throw WalletRejectedMoveAmountException.notEnoughForWalletName(
          walletSender.getWalletName());
    }
  }

  private void validationNotFound(MoveAmountWallet moveAmountWallet,
      List<WalletEntity> walletEntities) {
    if (walletEntities.isEmpty()) {
      log.debug("when both not found");
      throw new WalletNotFoundException("Both wallet not found");
    } else if (walletEntities.size() == 1) {
      log.debug("when only found 1");
      Long walletNotFound =
          Objects.equals(walletEntities.get(0).getId(), moveAmountWallet.walletIdReceive())
              ? moveAmountWallet.walletIdReceive()
              : moveAmountWallet.walletIdSender();
      throw WalletNotFoundException.forWalletId(walletNotFound);
    }
  }

  @Override
  public void deleteWallet(Long id) {
    Optional<WalletEntity> walletEntityOptional = walletRepository.findById(id);
    if (walletEntityOptional.isEmpty()) {
      throw WalletNotFoundException.forWalletId(id);
    }

    walletRepository.deleteById(id);
  }

  @Override
  public void updateBalanceWallet(UpdateWalletBalanceEvent updateWalletBalanceEvent) {
    Optional<WalletEntity> walletEntityOptional = walletRepository.findById(
        updateWalletBalanceEvent.id());

    if (walletEntityOptional.isPresent()) {
      log.debug("Event Update Balance");
      WalletEntity entity = walletEntityOptional.get();
      BigDecimal rollbackBalanceByTransactionCurrent = entity.getBalance()
          .min(updateWalletBalanceEvent.currentAmountTrx());
      BigDecimal updatedBalanceFinal = rollbackBalanceByTransactionCurrent.add(
          updateWalletBalanceEvent.updatedAmountTrx());
      entity.setBalance(updatedBalanceFinal);
      walletRepository.saveAndFlush(entity);
    }
  }
}
