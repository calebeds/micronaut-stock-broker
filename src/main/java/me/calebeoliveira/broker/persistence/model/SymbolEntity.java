package me.calebeoliveira.broker.persistence.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.calebeoliveira.api.RestApiResponse;

@Entity(name = "symbol")
@Table(name = "symbols", schema = "mn")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Serdeable
public class SymbolEntity implements RestApiResponse {
    @Id
    private String value;
}
