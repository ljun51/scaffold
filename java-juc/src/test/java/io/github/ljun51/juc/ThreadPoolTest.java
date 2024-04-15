package io.github.ljun51.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

    @Test
    public void singleThreadPoolTest() {
        // 创建单线程的线程池
        ExecutorService executorService = Executors.newSingleThreadExecutor();
    }

    @Test
    public void fixedThreadPoolTest() {
        // 创建固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
    }

    @Test
    public void scheduledThreadPoolTest() {
        // 创建可定时执行任务的线程池
        ExecutorService executorService = Executors.newScheduledThreadPool(2);
    }

    @Test
    public void cachedThreadPoolTest() {
        // 创建可自动伸缩的线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
    }

    @Test
    public void workingThreadPoolTest() {
        // 创建具有抢占式操作的线程池
        ExecutorService executorService = Executors.newWorkStealingPool();
    }
}
