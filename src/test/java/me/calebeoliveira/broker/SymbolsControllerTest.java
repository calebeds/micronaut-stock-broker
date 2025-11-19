package me.calebeoliveira.broker;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.json.tree.JsonNode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import me.calebeoliveira.broker.data.InMemoryStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MicronautTest
class SymbolsControllerTest {
    @Inject
    @Client("/symbols")
    HttpClient client;

    @Inject
    InMemoryStore memoryStore;

    @BeforeEach
    void setUp() {
        memoryStore.initializeWith(10);
    }

    @Test
    void shouldReturnListOfSymbols_whenCallingGetSymbolsEndpoint() {
        var response = client.toBlocking().exchange("/", JsonNode.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(10, response.getBody().get().size());
    }

    @Test
    void shouldReturnSymbol_whenCallingGetSymbolsByIdEndpoint() {
        var testSymbol = new Symbol("TEST");
        memoryStore.getSymbols().put(testSymbol.value(), testSymbol);

        var response = client.toBlocking().exchange("/" + testSymbol.value(), Symbol.class);
        assertEquals(HttpStatus.OK, response.getStatus());
        assertEquals(testSymbol, response.getBody().get());
    }
}