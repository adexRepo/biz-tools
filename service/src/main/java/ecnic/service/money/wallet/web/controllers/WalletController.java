package ecnic.service.money.wallet.web.controllers;

import ecnic.service.common.models.PagedResult;
import ecnic.service.money.wallet.WalletService;
import ecnic.service.money.wallet.domain.models.CreateWallet;
import ecnic.service.money.wallet.domain.models.MoveAmountWallet;
import ecnic.service.money.wallet.domain.models.Wallet;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wallet")
class WalletController {

  private final WalletService walletService;

  @GetMapping
  PagedResult<Wallet> wallets(@PageableDefault(value = 5, page = 0) Pageable pageable) {
    return walletService.getAllWallets(pageable);
  }

  @GetMapping("{id}")
  Wallet wallet(@PathVariable Long id) {
    return walletService.getWallet(id);
  }

  @PostMapping
  Wallet create(@RequestBody CreateWallet createWallet) {
    return walletService.createWallet(createWallet);
  }

  @PutMapping
  Wallet update(
      @RequestParam Long id,
      @RequestParam String newWalletName,
      @RequestParam BigDecimal newBalance) {
    return walletService.updateWallet(id, newWalletName, newBalance);
  }

  @PostMapping("move-amount")
  List<Wallet> moveAmount(@RequestBody @Valid MoveAmountWallet moveAmountWallet) {
    return walletService.moveAmount(moveAmountWallet);
  }

  @DeleteMapping
  void delete(@PathVariable Long id) {
    walletService.deleteWallet(id);
  }
}
