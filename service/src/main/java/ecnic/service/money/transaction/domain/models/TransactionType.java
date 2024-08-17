package ecnic.service.money.transaction.domain.models;

public enum TransactionType {
  INCOME,
  EXPENSES,
  INVEST;

  public enum ActionFunds {
    WITHDRAW,
    TRANSFER,
    DEPOSIT
  }
}
