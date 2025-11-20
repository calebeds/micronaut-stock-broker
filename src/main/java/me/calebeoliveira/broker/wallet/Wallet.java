package me.calebeoliveira.broker.wallet;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.api.RestApiResponse;
import me.calebeoliveira.broker.Symbol;

import java.math.BigDecimal;
import java.util.UUID;

@Serdeable
public record Wallet(
        UUID accountId,
        UUID walletId,
        Symbol symbol,
        BigDecimal available,
        BigDecimal locked
) implements RestApiResponse {
    public Wallet addAvailable(BigDecimal amountToAdd) {
        return new Wallet(
                this.accountId,
                this.walletId,
                this.symbol,
                this.available.add(amountToAdd),
                this.locked);
    }
}
