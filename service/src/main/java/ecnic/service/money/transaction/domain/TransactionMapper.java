package ecnic.service.money.transaction.domain;

import ecnic.service.money.transaction.domain.models.CreateTransaction;
import ecnic.service.money.transaction.domain.models.Transaction;
import ecnic.service.money.transaction.domain.models.UpdateTransaction;

final class TransactionMapper {

  private TransactionMapper() {
  }

  static Transaction convertToTransaction(TransactionEntity entity) {
    return new Transaction(
        entity.getId(),
        entity.getWalletId(),
        entity.getType(),
        entity.getAt(),
        entity.getItem(),
        entity.getOriginalAmount(),
        entity.getOtherAmounts(),
        entity.getExchangeRate()
    );
  }

  static TransactionEntity convertToEntity(CreateTransaction createTransaction) {
    TransactionEntity entity = new TransactionEntity();
    entity.setCreatedBy(createTransaction.userId().toString());
    entity.setModifiedBy(createTransaction.userId().toString());
    entity.setItem(createTransaction.item());
    entity.setType(createTransaction.type());
    entity.setOriginalAmount(createTransaction.originalAmount());
    entity.setAt(createTransaction.at());
    entity.setOtherAmounts(createTransaction.otherAmounts());
    entity.setExchangeRate(createTransaction.exchangeRate());
    entity.setRemark(createTransaction.remark());
    entity.setWalletId(createTransaction.walletId());
    return entity;
  }

  static void updateEntityTransaction(TransactionEntity entity,
      UpdateTransaction updateTransaction) {
    entity.setAt(updateTransaction.at());
    entity.setModifiedBy(updateTransaction.userId().toString());
    entity.setItem(updateTransaction.item());
    entity.setOriginalAmount(updateTransaction.originalAmount());
    entity.setExchangeRate(updateTransaction.exchangeRate());
    entity.setOtherAmounts(updateTransaction.otherAmounts());
  }

}
