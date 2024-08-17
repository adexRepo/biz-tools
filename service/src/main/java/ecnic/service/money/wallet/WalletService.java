package ecnic.service.money.wallet;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.transaction.domain.events.UpdateWalletBalanceEvent;
import ecnic.service.money.wallet.domain.models.CreateWallet;
import ecnic.service.money.wallet.domain.models.MoveAmountWallet;
import ecnic.service.money.wallet.domain.models.Wallet;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.modulith.events.ApplicationModuleListener;

public interface WalletService {

  PagedResult<Wallet> getAllWallets(Pageable pageable);

  Wallet getWallet(Long id);

  Wallet createWallet(CreateWallet createWallet);

  Wallet updateWallet(Long id, String newWalletName, BigDecimal newBalance);

  List<Wallet> moveAmount(MoveAmountWallet moveAmountWallet);

  void deleteWallet(Long id);

  @ApplicationModuleListener
  void updateBalanceWallet(UpdateWalletBalanceEvent updateWalletBalanceEvent);

}
