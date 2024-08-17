package ecnic.service.money;

import ecnic.service.money.wallet.domain.models.MoveAmountWallet;
import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Utils {

  private Utils() {
  }

  public static BigDecimal calculateExchangeRate(MoveAmountWallet moveAmountWallet) {
    if (moveAmountWallet.isDifferentCurrency()) {
      // this wise app scenario
      BigDecimal originalSent = moveAmountWallet.amountSent().min(moveAmountWallet.adminFee());
      return originalSent.divide(moveAmountWallet.amountReceived(), 3, RoundingMode.HALF_DOWN);
    }
    return BigDecimal.ONE;
  }

  public static BigDecimal calculateOriginalAmtWithExchangeRate(BigDecimal amount,
      BigDecimal otherAmounts,
      BigDecimal exchangeRate) {
    return amount.add(otherAmounts).subtract(exchangeRate);
  }


}
