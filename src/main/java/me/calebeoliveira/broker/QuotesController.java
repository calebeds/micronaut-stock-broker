package me.calebeoliveira.broker;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.calebeoliveira.api.RestApiResponse;
import me.calebeoliveira.broker.error.CustomError;
import me.calebeoliveira.broker.persistence.jpa.QuotesRepository;
import me.calebeoliveira.broker.persistence.model.QuoteDTO;
import me.calebeoliveira.broker.persistence.model.QuoteEntity;
import me.calebeoliveira.broker.persistence.model.SymbolEntity;

import java.util.List;
import java.util.Optional;

@Controller("/quotes")
@RequiredArgsConstructor
public class QuotesController {
    private final QuotesRepository quotesRepository;

    @Get("/jpa")
    public List<QuoteEntity> getAllQuotesViaJPA() {
        return quotesRepository.findAll();
    }

    @Operation(summary = "Returns a quote for the given symbol. Fetched from the database via jpa")
    @ApiResponse(content = @Content(mediaType = MediaType.APPLICATION_JSON))
    @ApiResponse(responseCode = "400", description = "Invalid symbol specified")
    @Tag(name = "quotes")
    @Get("/{symbol}/jpa")
    public HttpResponse<RestApiResponse> getQuotesViaJPA(@PathVariable String symbol) {
        final Optional<QuoteEntity> maybeQuote = quotesRepository.findBySymbol(new SymbolEntity(symbol));
        if(maybeQuote.isEmpty()) {
            final CustomError notFound = CustomError
                    .builder()
                    .status(HttpStatus.NOT_FOUND.getCode())
                    .error(HttpStatus.NOT_FOUND.name())
                    .message("quote for symbol not available in db")
                    .build();
            return HttpResponse.notFound(notFound);
        }

        return HttpResponse.ok(maybeQuote.get());
    }

    @Get("/jpa/ordered/desc")
    public List<QuoteDTO> orderedDesc() {
        return quotesRepository.listOrderByVolumeDesc();
    }

    @Get("/jpa/ordered/asc")
    public List<QuoteDTO> orderedAsc() {
        return quotesRepository.listOrderByVolumeAsc();
    }
}
