package dev.rgbmc.ultralucky.utils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class AsyncFuture<T> {

    private final Supplier<T> supplier;

    public AsyncFuture(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public CompletableFuture<T> execute() {
        return CompletableFuture.supplyAsync(supplier);
    }
}
