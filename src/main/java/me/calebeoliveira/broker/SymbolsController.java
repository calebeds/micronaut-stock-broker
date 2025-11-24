package me.calebeoliveira.broker;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.calebeoliveira.broker.data.InMemoryStore;
import me.calebeoliveira.broker.persistence.jpa.SymbolsRepository;
import me.calebeoliveira.broker.persistence.model.SymbolEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/symbols")
class SymbolsController {

    private final InMemoryStore inMemoryStore;
    private final SymbolsRepository symbolRepository;

    public SymbolsController(final InMemoryStore inMemoryStore,
                             final SymbolsRepository symbolsRepository) {
        this.inMemoryStore = inMemoryStore;
        this.symbolRepository = symbolsRepository;
    }

    @Get
    public List<Symbol> getAll() {
        return new ArrayList<>(inMemoryStore.getSymbols().values());
    }

    @Get("{value}")
    public Symbol getSymbolByValue(@PathVariable String value) {
        return inMemoryStore.getSymbols().get(value);
    }

    @Get("/filter{?max,offset}")
    public List<Symbol> getSymbols(@QueryValue Optional<Integer> max,
                                   @QueryValue Optional<Integer> offset) {
        return inMemoryStore.getSymbols().values()
                .stream()
                .skip(offset.orElse(0))
                .limit(max.orElse(10))
                .toList();
    }

    @Operation(summary = "Return all available markets from database using jpa")
    @ApiResponse(
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Tag(name = "markets")
    @Get("/jpa")
    public List<SymbolEntity> allSymbolsViaJPA() {
        return symbolRepository.findAll();
    }

}
