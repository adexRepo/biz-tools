package ecnic.service.money.wallet.domain.exceptions;

public class WalletRejectedMoveAmountException extends RuntimeException {

  public WalletRejectedMoveAmountException(String message) {
    super(message);
  }

  public static WalletRejectedMoveAmountException notEnoughForWalletName(String walletName) {
    return new WalletRejectedMoveAmountException("Wallet " + walletName + " balance not enough");
  }
}
