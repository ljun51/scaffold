# CountDownLatch

`CountDownLatch` 是 Java 中用作多线程倒计时计数器的一种工具。它强制多个线程等待其他一组任务执行完成。这里有一些关键点需要说明：

1. **初始化不可复原**：一旦 `CountDownLatch` 初始化后，计数器值递减到 0，就不能再恢复。这与 `Semaphore` 不同，后者可以通过 `release` 操作恢复信号量。

2. **使用原理**：
    - 创建 `CountDownLatch` 并设置计数器值。
    - 启动多线程并调用 `CountDownLatch` 实例的 `countDown()` 方法。
    - 主线程调用 `await()` 方法，阻塞在此处，直到其他线程完成各自的任务，使计数值为 0，然后主线程继续执行。

3. **示例模板**：
```java
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CountDownLatchCase1 {
    private static final int N = 10; // 线程数
    private static final int countDownLatchTimeout = 5; // 单位：分钟

    public static void main(String[] args) {
        // 创建CountDownLatch并设置计数值，该count值可以根据线程数的需要设置
        CountDownLatch countDownLatch = new CountDownLatch(N);

        // 创建线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < N; i++) {
            cachedThreadPool.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " do something!");
                } catch (Exception e) {
                    System.out.println("Exception: do something exception");
                } finally {
                    // 该线程执行完毕-1
                    countDownLatch.countDown();
                }
            });
        }

        System.out.println("main thread do something-1");
        try {
            countDownLatch.await(countDownLatchTimeout, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.out.println("Exception: await interrupted exception");
        } finally {
            System.out.println("countDownLatch: " + countDownLatch.toString());
        }
        System.out.println("main thread do something-2");

        // 若需要停止线程池可关闭
        // cachedThreadPool.shutdown();
    }
}
```

4. **常用方法**：
    - `await()`: 调用此方法的线程会被挂起，等待直到计数值为 0 后继续执行。
    - `await(long timeout, TimeUnit unit)`: 若等待 `timeout` 时长后，计数值仍未变为 0，不再等待，继续执行。
    - `countDown()`: 计数值递减 1。
    - `getCount()`: 获取当前计数值。

`CountDownLatch` 的典型应用场景是一个程序中有多个任务在执行，我们可以创建值为 N 的 `CountDownLatch`，当每个任务完成后，调用 `countDown()` 方法进行递减，主线程使用 `await()` 方法等待任务执行完成，然后继续执行.
