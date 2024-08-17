package ecnic.service.money.transaction;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.transaction.domain.models.CreateTransaction;
import ecnic.service.money.transaction.domain.models.Transaction;
import ecnic.service.money.transaction.domain.models.UpdateTransaction;
import ecnic.service.money.wallet.domain.events.MoveAmountWalletEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.modulith.events.ApplicationModuleListener;

public interface TransactionService {

  PagedResult<Transaction> getAllTransaction(Pageable pageable);

  Transaction getTransaction(Long id);

  Transaction updateTransaction(UpdateTransaction updateTransaction);

  Transaction createTransaction(CreateTransaction createTransaction);

  void deleteTransaction(Long id);

  @ApplicationModuleListener
  void moveAmountWalletEvent(MoveAmountWalletEvent moveAmountWalletEvent);

}
