package me.calebeoliveira.broker.watchlist;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import me.calebeoliveira.broker.data.InMemoryAccountStore;

import static me.calebeoliveira.constants.Constants.ACCOUNT_ID;

@Controller("/account/watchlist")
public class WatchListController {

    private final InMemoryAccountStore store;

    public WatchListController(InMemoryAccountStore store) {
        this.store = store;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public WatchList get() {
        return store.getWatchList(ACCOUNT_ID);
    }

    @Put(consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON)
    public WatchList update(@Body WatchList watchList) {
        store.updateWatchList(ACCOUNT_ID, watchList);
        return store.getWatchList(ACCOUNT_ID);
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete
    public void delete() {
        store.deleteWatchList(ACCOUNT_ID);
    }
}
