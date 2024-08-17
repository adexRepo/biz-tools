package ecnic.service.money.transaction.domain;

import static ecnic.service.money.Utils.calculateOriginalAmtWithExchangeRate;
import static ecnic.service.money.transaction.domain.TransactionMapper.convertToEntity;
import static ecnic.service.money.transaction.domain.TransactionMapper.convertToTransaction;
import static ecnic.service.money.transaction.domain.TransactionMapper.updateEntityTransaction;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.transaction.TransactionService;
import ecnic.service.money.transaction.domain.events.UpdateWalletBalanceEvent;
import ecnic.service.money.transaction.domain.exception.TransactionNotFoundException;
import ecnic.service.money.transaction.domain.models.CreateTransaction;
import ecnic.service.money.transaction.domain.models.Transaction;
import ecnic.service.money.transaction.domain.models.TransactionType;
import ecnic.service.money.transaction.domain.models.UpdateTransaction;
import ecnic.service.money.wallet.domain.events.MoveAmountWalletEvent;
import ecnic.service.money.wallet.domain.models.WalletType;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TransactionServiceImpl implements TransactionService {

  public static final String MOVE_AMOUNT_WALLET = "MOVE AMOUNT WALLET";
  private final TransactionRepository transactionRepository;
  private final ApplicationEventPublisher eventPublisher;

  @Override
  public PagedResult<Transaction> getAllTransaction(Pageable pageable) {
    return new PagedResult<>(transactionRepository.findAllBy(pageable));
  }

  @Override
  public Transaction getTransaction(Long id) {
    Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(id);
    if (optionalTransactionEntity.isEmpty()) {
      throw TransactionNotFoundException.forTransactionId(id);
    }
    return convertToTransaction(optionalTransactionEntity.get());
  }

  @Override
  public Transaction updateTransaction(UpdateTransaction updateTransaction) {
    Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(
        updateTransaction.id());
    if (optionalTransactionEntity.isEmpty()) {
      throw TransactionNotFoundException.forTransactionId(updateTransaction.id());
    }
    TransactionEntity entity = optionalTransactionEntity.get();
    if (entity.getOriginalAmount().compareTo(updateTransaction.originalAmount()) != 0
        || entity.getOtherAmounts().compareTo(updateTransaction.otherAmounts()) != 0
        || entity.getExchangeRate().compareTo(updateTransaction.exchangeRate()) != 0
    ) {

      // event to wallet to change
      BigDecimal currentAmountTrx = calculateOriginalAmtWithExchangeRate(entity.getOriginalAmount(),
          entity.getOtherAmounts(),
          entity.getExchangeRate());
      BigDecimal updateAmountTrx = calculateOriginalAmtWithExchangeRate(
          updateTransaction.originalAmount(),
          updateTransaction.otherAmounts(), updateTransaction.exchangeRate());

      eventPublisher.publishEvent(
          new UpdateWalletBalanceEvent(
              entity.getWalletId(), updateTransaction.userId(), currentAmountTrx, updateAmountTrx)
      );
    }

    updateEntityTransaction(entity, updateTransaction);
    transactionRepository.saveAndFlush(entity);
    return convertToTransaction(entity);
  }

  @Override
  public void deleteTransaction(Long id) {
    Optional<TransactionEntity> optionalTransactionEntity = transactionRepository.findById(id);
    if (optionalTransactionEntity.isEmpty()) {
      throw TransactionNotFoundException.forTransactionId(id);
    }

    transactionRepository.deleteById(id);
  }

  @Override
  public Transaction createTransaction(CreateTransaction createTransaction) {
    TransactionEntity transactionEntity = convertToEntity(createTransaction);
    transactionEntity = transactionRepository.saveAndFlush(transactionEntity);
    return convertToTransaction(transactionEntity);
  }

  @Override
  public void moveAmountWalletEvent(MoveAmountWalletEvent moveAmountWalletEvent) {
    final String Action_Funds = String.valueOf(
        moveAmountWalletEvent.walletReceiveType().equals(WalletType.CASH)
            ? TransactionType.ActionFunds.WITHDRAW : TransactionType.ActionFunds.TRANSFER);
    final String Remark_Move = String.format("%s-%s", Action_Funds, Instant.now().toEpochMilli());
    // wallet money sender
    CreateTransaction createTrxWalletFrom = new CreateTransaction(
        moveAmountWalletEvent.idUser(),
        moveAmountWalletEvent.idWalletSender(),
        TransactionType.EXPENSES,
        moveAmountWalletEvent.at(),
        MOVE_AMOUNT_WALLET,
        moveAmountWalletEvent.amountSent(),
        moveAmountWalletEvent.exchangeRate(),
        moveAmountWalletEvent.adminFee(),
        Remark_Move
    );
    createTransaction(createTrxWalletFrom);

    // wallet money receive
    CreateTransaction createTrxWalletTo = new CreateTransaction(
        moveAmountWalletEvent.idUser(),
        moveAmountWalletEvent.idWalletReceive(),
        TransactionType.INCOME,
        moveAmountWalletEvent.at(),
        MOVE_AMOUNT_WALLET,
        moveAmountWalletEvent.amountReceived(),
        BigDecimal.ONE,
        BigDecimal.ZERO,
        Remark_Move
    );
    createTransaction(createTrxWalletTo);
  }
}
