package me.calebeoliveira.broker.wallet;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.api.RestApiResponse;
import me.calebeoliveira.broker.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public record DepositFiatMoney(
        UUID accountId,
        UUID walletId,
        Symbol symbol,
        BigDecimal amount
) {
}
