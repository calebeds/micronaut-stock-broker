package me.calebeoliveira.broker.persistence.model;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Introspected
@Serdeable
@Getter
@Setter
public class QuoteDTO {
    private Integer id;
    private BigDecimal volume;
}
