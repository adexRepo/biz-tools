package ecnic.service.money.wallet.domain.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import org.springframework.boot.context.properties.bind.DefaultValue;

public record MoveAmountWallet(
    @NotNull(message = "required field")
    Long idUser,
    @NotNull(message = "required field")
    Long walletIdSender,
    @NotNull(message = "required field")
    Long walletIdReceive,
    @NotNull(message = "required field")
    ZonedDateTime at,
    @NotNull(message = "required field")
    @DefaultValue("Operational Purpose")
    String remark,
    @NotNull(message = "required field")
    @DecimalMin(value = "1", message = "Amount Sent must be greater than or equal to 1")
    BigDecimal amountSent,
    @NotNull(message = "required field")
    @DecimalMin(value = "1", message = "Amount Received must be greater than or equal to 1")
    BigDecimal amountReceived,
    @NotNull(message = "required  field")
    @DecimalMin(value = "0", message = "Admin Fee must be greater than or equal to 0")
    BigDecimal adminFee,
    @NotNull(message = "required isDifferentCurrency field")
    boolean isDifferentCurrency
) {

}
