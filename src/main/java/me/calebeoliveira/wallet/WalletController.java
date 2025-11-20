package me.calebeoliveira.wallet;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import me.calebeoliveira.wallet.error.CustomError;
import me.calebeoliveira.watchlist.InMemoryAccountStore;

import java.util.Collection;
import java.util.List;

import static me.calebeoliveira.constants.Constants.ACCOUNT_ID;

@Controller("/account/wallets")
public class WalletController {

    public static final List<String> SUPPORTED_FIAT_CURRENCIES = List.of("EUR", "USD", "CHF", "GBP");
    private final InMemoryAccountStore store;

    public WalletController(InMemoryAccountStore store) {
        this.store = store;
    }

    @Get
    public Collection<Wallet> get() {
        return store.getWallets(ACCOUNT_ID);
    }

    @Post("/deposit")
    public HttpResponse<CustomError> depositFiatMoney(@Body DepositFiatMoney deposit) {
        if(!SUPPORTED_FIAT_CURRENCIES.contains(deposit.symbol().value())) {
            return HttpResponse.badRequest()
                    .body(new CustomError(
                            HttpStatus.BAD_REQUEST.getCode(),
                            "UNSUPPORTED_FIAT_CURRENCY",
                            String.format("Only %s are supported", SUPPORTED_FIAT_CURRENCIES)));
        }
        return HttpResponse.ok();
    }

    @Post("/withdraw")
    public void withdrawFiatMoney(@Body WithdrawalFiatMoney withdraw) {

    }
}
