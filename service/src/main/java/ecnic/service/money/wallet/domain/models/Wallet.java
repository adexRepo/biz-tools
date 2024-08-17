package ecnic.service.money.wallet.domain.models;

import java.math.BigDecimal;

public record Wallet(Long id, String walletName, BigDecimal amount) {
}
