package me.calebeoliveira.broker.persistence.jpa;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.calebeoliveira.broker.persistence.model.QuoteEntity;
import me.calebeoliveira.broker.persistence.model.SymbolEntity;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

/**
 * Used to insert data into db on startup
 */
@Singleton
@Requires(notEnv = Environment.TEST)
@RequiredArgsConstructor
@Slf4j
public class TestDataProvider {
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private final SymbolsRepository symbolsRepository;
    private final QuotesRepository quotesRepository;


    @EventListener
    public void init(StartupEvent event) {
        if(symbolsRepository.findAll().isEmpty()) {
            log.info("Adding test data as empty database was found!");
            Stream.of("AAPL", "AM2N", "FB", "TSLA")
                    .map(SymbolEntity::new)
                    .forEach(symbolsRepository::save);
        }

        if(quotesRepository.findAll().isEmpty()) {
            log.info("Adding test data as empty database was found!");
            symbolsRepository.findAll().forEach(symbolEntity -> {
                var quote = new QuoteEntity();
                quote.setSymbol(symbolEntity);
                quote.setAsk(randomValue());
                quote.setBid(randomValue());
                quote.setLastPrice(randomValue());
                quote.setVolume(randomValue());
                quotesRepository.save(quote);
            });
        }
    }

    private BigDecimal randomValue() {
        return BigDecimal.valueOf(RANDOM.nextDouble(1, 100));
    }
}
