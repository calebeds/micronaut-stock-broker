package me.calebeoliveira.broker;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import me.calebeoliveira.broker.data.InMemoryStore;

import java.util.ArrayList;
import java.util.List;

@Controller("/symbols")
class SymbolsController {

    private final InMemoryStore inMemoryStore;

    public SymbolsController(InMemoryStore inMemoryStore) {
        this.inMemoryStore = inMemoryStore;
    }

    @Get
    public List<Symbol> getAll() {
        return new ArrayList<>(inMemoryStore.getSymbols().values());
    }
}
