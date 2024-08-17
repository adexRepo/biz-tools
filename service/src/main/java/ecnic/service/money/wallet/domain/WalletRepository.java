package ecnic.service.money.wallet.domain;

import ecnic.service.money.wallet.domain.models.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface WalletRepository extends JpaRepository<WalletEntity, Long> {
  @Query("""
        select new ecnic.service.money.wallet.domain.models.Wallet(
            wallet.id,
            wallet.walletName,
            wallet.balance
        ) from WalletEntity wallet
        """)
  Page<Wallet> findAllBy(Pageable pageable);
}
