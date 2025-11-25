package me.calebeoliveira.broker.persistence.jpa;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;
import me.calebeoliveira.broker.persistence.model.QuoteDTO;
import me.calebeoliveira.broker.persistence.model.QuoteEntity;
import me.calebeoliveira.broker.persistence.model.SymbolEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuotesRepository extends CrudRepository<QuoteEntity, Integer> {
    Optional<QuoteEntity> findBySymbol(SymbolEntity symbol);

    // Ordering
    List<QuoteDTO> listOrderByVolumeDesc();
    List<QuoteDTO> listOrderByVolumeAsc();

    // Filter
    List<QuoteDTO> findByVolumeGreaterThanOrderByVolumeAsc(BigDecimal volume);

    // Pagination
    List<QuoteDTO> findByVolumeGreaterThan(BigDecimal volume, Pageable pageable);

    Slice<QuoteDTO> list(Pageable pageable);
}
