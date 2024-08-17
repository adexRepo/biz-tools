package ecnic.service.money.wallet.domain.events;

import ecnic.service.money.wallet.domain.models.WalletType;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record MoveAmountWalletEvent(
    Long idUser,
    Long idWalletSender,
    Long idWalletReceive,
    WalletType walletSenderType,
    WalletType walletReceiveType,
    ZonedDateTime at,
    String remark,
    BigDecimal amountSent,
    BigDecimal amountReceived,
    BigDecimal adminFee,
    BigDecimal exchangeRate
) {

}
