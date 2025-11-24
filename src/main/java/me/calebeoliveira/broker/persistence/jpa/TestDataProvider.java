package me.calebeoliveira.broker.persistence.jpa;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * Used to insert data into db on startup
 */
@Singleton
@Requires(notEnv = Environment.TEST)
@RequiredArgsConstructor
@Slf4j
public class TestDataProvider {
    private final SymbolsRepository symbolsRepository;

    @EventListener
    public void init(StartupEvent event) {
        if(symbolsRepository.findAll().isEmpty()) {
            log.info("Adding test data as empty database was found!");
            Stream.of("AAPL", "AM2N", "FB", "TSLA")
                    .map(SymbolEntity::new)
                    .forEach(symbolsRepository::save);
        }
    }
}
