package me.calebeoliveira.broker.error;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;
import me.calebeoliveira.api.RestApiResponse;

@Serdeable
@Builder
public record CustomError(
        int status,
        String error,
        String message
) implements RestApiResponse {
}
