package me.calebeoliveira.broker.wallet.error;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.api.RestApiResponse;

@Serdeable
public record CustomError(
        int status,
        String error,
        String message
) implements RestApiResponse {
}
