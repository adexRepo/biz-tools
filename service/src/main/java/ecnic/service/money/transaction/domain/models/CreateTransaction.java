package ecnic.service.money.transaction.domain.models;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record CreateTransaction(
    @NotNull(message = "Required field") Long userId,
    @NotNull(message = "Required field") Long walletId,
    @NotNull(message = "Required field") TransactionType type,
    @NotNull(message = "Required field") ZonedDateTime at,
    @NotNull(message = "Required field") String item,
    @NotNull(message = "Required field") BigDecimal originalAmount, // main item
    @NotNull(message = "Required field") BigDecimal exchangeRate,
    BigDecimal otherAmounts, // admin or fee
    String remark // main item
) {

}
