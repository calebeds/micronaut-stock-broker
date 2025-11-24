package me.calebeoliveira.broker.persistence.jpa;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import me.calebeoliveira.broker.persistence.model.QuoteEntity;
import me.calebeoliveira.broker.persistence.model.SymbolEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuotesRepository extends CrudRepository<QuoteEntity, Integer> {
    Optional<QuoteEntity> findBySymbol(SymbolEntity symbol);

    // Ordering
    List<QuoteEntity> listOrderByVolumeDesc();
    List<QuoteEntity> listOrderByVolumeAsc();
}
