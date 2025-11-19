package me.calebeoliveira.watchlist;

import io.micronaut.serde.annotation.Serdeable;
import me.calebeoliveira.broker.Symbol;

import java.util.ArrayList;
import java.util.List;

@Serdeable
public record WatchList(List<Symbol> symbols) {
    public WatchList() {
        this(new ArrayList<>());
    }
}
