package ecnic.service.money.transaction.domain.events;

import java.math.BigDecimal;

public record UpdateWalletBalanceEvent(
    Long id,
    Long userId,
    BigDecimal currentAmountTrx,
    BigDecimal updatedAmountTrx
){

}
