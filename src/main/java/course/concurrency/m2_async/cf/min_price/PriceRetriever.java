package course.concurrency.m2_async.cf.min_price;

import java.util.concurrent.*;

public class PriceRetriever {

    public double getPrice(long itemId, long shopId) {
        int delay = ThreadLocalRandom.current().nextInt(10);
        sleep(delay);
        return ThreadLocalRandom.current().nextDouble(1000);
    }

    private void sleep(int delay) {
        try { Thread.sleep(delay * 1000);
        } catch (InterruptedException e) {}
    }

    public static long longTask() throws InterruptedException {
        Thread.sleep(1000); // + try-catch
        return ThreadLocalRandom.current().nextInt();
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(Integer.MAX_VALUE));

        for (int i = 0; i < 10; i++) {
            executor.submit(() -> longTask());
            System.out.print(executor.getPoolSize() + " ");
        }
        executor.shutdown();
    }
}
