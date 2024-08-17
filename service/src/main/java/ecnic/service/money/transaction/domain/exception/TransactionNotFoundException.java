package ecnic.service.money.transaction.domain.exception;

public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(String message) {
    super(message);
  }

  public static TransactionNotFoundException forTransactionId(Long walletId) {
    return new TransactionNotFoundException(
        "Transaction with ID Number " + walletId + " not found");
  }
}
