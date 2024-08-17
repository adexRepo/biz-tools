package ecnic.service.money.transaction.domain;

import ecnic.service.money.transaction.domain.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

  @Query("""
      select new ecnic.service.money.transaction.domain.models.Transaction(
          trx.id,
          trx.walletId,
          trx.type,
          trx.at,
          trx.item,
          trx.originalAmount,
          trx.otherAmounts,
          trx.exchangeRate
      ) from TransactionEntity trx
      """)
  Page<Transaction> findAllBy(Pageable pageable);
}
