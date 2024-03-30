package io.github.ljun51.concurrent;

import java.util.concurrent.*;

/**
 * @author lee
 */
public class FutureCase1 {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                long start = System.currentTimeMillis();
                while (true) {
                    long current = System.currentTimeMillis();
                    if ((current - start) > 1000) {
                        return 1;
                    }
                }
            }
        });

        try {
            int result = (int) future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
