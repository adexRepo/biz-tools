package ecnic.service.money.transaction.domain.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record Transaction(
    Long id,
    Long walletId,
    TransactionType type,
    ZonedDateTime at,
    String item,
    BigDecimal originalAmount,
    BigDecimal otherAmounts, // admin or fee
    BigDecimal exchangeRate
) {

}
