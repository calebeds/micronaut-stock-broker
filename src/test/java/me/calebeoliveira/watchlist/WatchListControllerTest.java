package me.calebeoliveira.watchlist;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import me.calebeoliveira.broker.Symbol;
import me.calebeoliveira.broker.data.InMemoryAccountStore;
import me.calebeoliveira.broker.watchlist.WatchList;
import me.calebeoliveira.constants.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
class WatchListControllerTest {
    private static final UUID TEST_ACCOUNT_ID = Constants.ACCOUNT_ID;

    @Inject
    @Client("/account/watchlist")
    HttpClient client;

    @Inject
    InMemoryAccountStore store;

    @BeforeEach
    void setUp() {
        store.deleteWatchList(TEST_ACCOUNT_ID);
    }

    @Test
    void shouldReturnEmptyWatchList_whenCallingGetWatchListEndpoint() {
        var response = client.toBlocking().exchange("/", WatchList.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertNull(response.getBody().get().symbols());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());
    }

    @Test
    void shouldReturnWatchList_whenTestAccountIsInMemoryStore() {
        givenWatchListForAccountExists();
        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
    }

    @Test
    void shouldUpdateWatch_whenUsingTesAccount() {
        final var symbols = Stream.of("APPL", "GOOGL", "MSFT").map(Symbol::new).toList();
        final var request = HttpRequest.PUT("/", new WatchList(symbols));

        final var response = client.toBlocking().exchange(request);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(symbols, store.getWatchList(TEST_ACCOUNT_ID).symbols());
    }

    @Test
    void shouldDeleteWatchList_whenCallingDeleteEndpoint() {
        givenWatchListForAccountExists();
        assertFalse(store.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());

        final var deleted = client.toBlocking().exchange(HttpRequest.DELETE("/"));

        assertEquals(HttpStatus.NO_CONTENT, deleted.getStatus());
        assertTrue(store.getWatchList(TEST_ACCOUNT_ID).symbols().isEmpty());
    }

    private void givenWatchListForAccountExists() {
        store.updateWatchList(TEST_ACCOUNT_ID, new WatchList(
                Stream.of("AAPL", "GOOGL", "MSFT")
                        .map(Symbol::new)
                        .toList()
        ));
    }
}