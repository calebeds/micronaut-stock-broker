package me.calebeoliveira.broker.error;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.api.RestApiResponse;

@Serdeable
public record CustomError(
        int status,
        String error,
        String message
) implements RestApiResponse {
}
