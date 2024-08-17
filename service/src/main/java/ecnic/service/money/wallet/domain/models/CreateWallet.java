package ecnic.service.money.wallet.domain.models;

import java.math.BigDecimal;

public record CreateWallet(
    Long id,
    Long walletUser,
    WalletType walletType,
    String walletName,
    BigDecimal initialBalance,
    String defaultCurrency
) {

}
