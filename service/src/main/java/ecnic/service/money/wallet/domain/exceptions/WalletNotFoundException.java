package ecnic.service.money.wallet.domain.exceptions;

public class WalletNotFoundException extends RuntimeException {

  public WalletNotFoundException(String message) {
    super(message);
  }

  public static WalletNotFoundException forWalletId(Long walletId) {
    return new WalletNotFoundException("Wallet with ID Number " + walletId + " not found");
  }

  public static WalletNotFoundException forWalletName(String walletName) {
    return new WalletNotFoundException("Wallet with Name " + walletName + " not found");
  }
}
