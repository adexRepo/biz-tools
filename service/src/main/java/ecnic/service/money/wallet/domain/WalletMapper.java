package ecnic.service.money.wallet.domain;

import static ecnic.service.money.Utils.calculateExchangeRate;

import ecnic.service.money.wallet.domain.events.MoveAmountWalletEvent;
import ecnic.service.money.wallet.domain.models.CreateWallet;
import ecnic.service.money.wallet.domain.models.MoveAmountWallet;
import ecnic.service.money.wallet.domain.models.Wallet;
import java.math.BigDecimal;
import java.util.List;

final class WalletMapper {

  private WalletMapper() {
  }

  static Wallet convertToWallet(WalletEntity walletEntity) {
    return new Wallet(walletEntity.getId(), walletEntity.getWalletName(),
        walletEntity.getBalance());
  }

  static List<Wallet> convertToWallets(List<WalletEntity> walletEntities) {
    return walletEntities.stream().map(WalletMapper::convertToWallet).toList();
  }

  static WalletEntity convertToEntity(CreateWallet createWallet) {
    WalletEntity entity = new WalletEntity();
    entity.setWalletName(createWallet.walletName());
    entity.setWalletType(createWallet.walletType());
    entity.setBalance(createWallet.initialBalance());
    entity.setWalletUserId(createWallet.walletUser());
    entity.setDefaultCurrency(createWallet.defaultCurrency());
    entity.setCreatedBy(createWallet.walletUser().toString());
    entity.setModifiedBy(createWallet.walletUser().toString());
    return entity;
  }

  static void updateEntity(WalletEntity entity, BigDecimal amount, String newWalletName) {
    entity.setBalance(amount);
    entity.setWalletName(newWalletName);
  }

  static MoveAmountWalletEvent toEventMoveAmountWallet(WalletEntity walletIdSender,
      WalletEntity walletIdReceive,
      MoveAmountWallet moveAmountWallet) {
    return new MoveAmountWalletEvent(
        moveAmountWallet.idUser(),
        walletIdSender.getId(),
        walletIdReceive.getId(),
        walletIdSender.getWalletType(),
        walletIdReceive.getWalletType(),
        moveAmountWallet.at(),
        moveAmountWallet.remark(),
        moveAmountWallet.amountSent(), // original sent
        moveAmountWallet.amountReceived(),
        moveAmountWallet.adminFee(),
        calculateExchangeRate(moveAmountWallet)
    );
  }

}
