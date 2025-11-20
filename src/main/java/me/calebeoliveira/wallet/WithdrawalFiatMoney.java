package me.calebeoliveira.wallet;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.broker.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

public record WithdrawalFiatMoney(
        UUID accountId,
        UUID walletId,
        Symbol symbol,
        BigDecimal available
) {
}
