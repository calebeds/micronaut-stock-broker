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

    @Test
    void shouldReturnFilteredSymbols_whenUsingQueryParameterMax() {
        var max10 = client.toBlocking().exchange("/filter?max=10", JsonNode.class);
        assertEquals(HttpStatus.OK, max10.getStatus());
        assertEquals(10, max10.getBody().get().size());
    }

    @Test
    void shouldReturnFilteredSymbols_whenUsingQueryParameterOffset() {
        var offset7 = client.toBlocking().exchange("/filter?offset=7", JsonNode.class);
        assertEquals(HttpStatus.OK, offset7.getStatus());
        assertEquals(3, offset7.getBody().get().size());
    }

    @Test
    void shouldReturnFilteredSymbols_whenUsingQueryParametersMaxAndOffset() {
        var max2Offset7 = client.toBlocking().exchange("/filter?max=2&offset=7", JsonNode.class);
        assertEquals(HttpStatus.OK, max2Offset7.getStatus());
        assertEquals(2, max2Offset7.getBody().get().size());
    }
}