package ecnic.service.money.transaction.domain.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record UpdateTransaction(
    Long id,
    Long userId,
    ZonedDateTime at,
    String item,
    BigDecimal originalAmount,
    BigDecimal otherAmounts, // admin or fee
    BigDecimal exchangeRate
) {

}
