package me.calebeoliveira.broker.data;

import jakarta.inject.Singleton;
import me.calebeoliveira.broker.Symbol;
import me.calebeoliveira.broker.wallet.DepositFiatMoney;
import me.calebeoliveira.broker.wallet.Wallet;
import me.calebeoliveira.broker.wallet.WithdrawalFiatMoney;
import me.calebeoliveira.broker.watchlist.WatchList;

import java.math.BigDecimal;
import java.util.*;

import static me.calebeoliveira.constants.Constants.ACCOUNT_ID;

@Singleton
public class InMemoryAccountStore {

    private static final HashMap<UUID, WatchList> watchListsPerAccount = new HashMap<>();
    private static final Map<UUID, Map<UUID, Wallet>> walletsPerAccount = new HashMap<>();

    public WatchList getWatchList(UUID accountId) {
        return watchListsPerAccount.getOrDefault(accountId, new WatchList());
    }

    public void updateWatchList(final UUID accountId, final WatchList watchList) {
        watchListsPerAccount.put(accountId, watchList);
    }

    public void deleteWatchList(final UUID accountId) {
        watchListsPerAccount.remove(accountId);
    }

    public Collection<Wallet> getWallets(UUID accountId) {
        return Optional.ofNullable(walletsPerAccount.get(accountId))
                .orElse(new HashMap<>())
                .values();
    }

    public Wallet depositToWallet(DepositFiatMoney deposit) {
        return changeAvailableInWallet(deposit.accountId(), deposit.walletId(), deposit.symbol(), deposit.amount());
    }

    public Wallet withdrawFromWallet(WithdrawalFiatMoney withdraw) {
        return changeAvailableInWallet(withdraw.accountId(), withdraw.walletId(), withdraw.symbol(), withdraw.amount());
    }

    private static Wallet changeAvailableInWallet(UUID accountId, UUID walletId, Symbol symbol, BigDecimal amount) {
        final var wallets = Optional.ofNullable(
                walletsPerAccount.get(accountId)
        ).orElse(new HashMap<>());

        final var oldWallet = Optional.ofNullable(wallets.get(walletId))
                .orElse(new Wallet(ACCOUNT_ID, walletId, symbol, BigDecimal.ZERO, BigDecimal.ZERO));

        final Wallet newWallet = oldWallet.addAvailable(amount);

        // update wallet in store
        wallets.put(newWallet.walletId(), newWallet);
        walletsPerAccount.put(newWallet.accountId(), wallets);

        return newWallet;
    }
}
