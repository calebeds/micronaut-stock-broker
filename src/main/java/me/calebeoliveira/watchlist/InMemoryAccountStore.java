package me.calebeoliveira.watchlist;

import jakarta.inject.Singleton;
import me.calebeoliveira.wallet.Wallet;

import java.util.*;

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
}
