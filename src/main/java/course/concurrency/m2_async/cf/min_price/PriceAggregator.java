package course.concurrency.m2_async.cf.min_price;

import java.util.*;
import java.util.concurrent.*;

public class PriceAggregator {

    private PriceRetriever priceRetriever = new PriceRetriever();

    public void setPriceRetriever(PriceRetriever priceRetriever) {
        this.priceRetriever = priceRetriever;
    }

    private Collection<Long> shopIds = Set.of(10l, 45l, 66l, 345l, 234l, 333l, 67l, 123l, 768l);

    public void setShops(Collection<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public double getMinPrice(long itemId) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<CompletableFuture<Void>> list = new ArrayList<>();
        List<Double> result = new CopyOnWriteArrayList<>();
        shopIds.stream().parallel().forEach(shopId -> {
                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> priceRetriever.getPrice(itemId, shopId), executorService)
                        .thenAccept(result::add).exceptionally(ex -> {
                            System.out.println(ex.getMessage());
                            return null;
                        });
                list.add(future);
        });
        try {
            CompletableFuture.allOf(list.toArray(new CompletableFuture[0]))
                    .get(2950, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
        }
        if (result.isEmpty()) {
            return Double.NaN;
        }
        return result.stream().min(Comparator.comparing(Double::valueOf)).orElse(Double.NaN);
    }
}
