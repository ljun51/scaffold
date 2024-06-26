# ThreadPoolExecutor

## 为什么使用线程池？
线程池是一种利用池化技术思想来实现的线程管理技术，主要是为了复用线程、便利地管理线程和任务，并将线程的创建和任务的执行解耦开来。让我们深入探讨一下线程池的优点和原理：

1. **优点**：
    - **降低资源消耗**：通过重用已经创建的线程，降低线程创建和销毁所带来的资源消耗。
    - **提高响应速度**：当有任务到达时，线程池可以立即执行，无需等待新线程的创建。
    - **方便线程并发数的管控**：线程池可以统一分配、调优和监控线程。

2. **为什么使用线程池？**
   在没有线程池之前，每次执行任务都需要创建新的线程，这会导致以下问题：
    - **不受控制的风险**：每个创建的线程没有统一管理的地方，我们不知道线程的去向。
    - **高开销**：创建线程对系统来说开销很高，因为线程的创建需要时间。
    - **资源浪费**：频繁创建和销毁线程会消耗大量时间和内存空间。

   为了避免这些问题，线程池应运而生。它允许我们复用已经创建的线程，提高系统性能，降低资源消耗，同时方便管理和控制线程的数量。

## 为什么不建议使用 `Executors` 创建线程池？

`Executors` 是 Java 中的一个工具类，提供了工厂方法来创建不同类型的线程池。它包含了一些常用的线程池创建方法，例如：

1. `newFixedThreadPool(int nThreads)`：创建固定数目线程的线程池。
2. `newCachedThreadPool()`：创建一个可缓存的线程池，会重用已有的线程。
3. `newSingleThreadExecutor()`：创建一个单线程化的线程池。
4. `newScheduledThreadPool(int corePoolSize)`：创建支持定时和周期性任务执行的线程池。

虽然 `Executors` 功能强大且方便，但是在实际开发中，**不建议直接使用 `Executors` 创建线程池**，而应该使用 `ThreadPoolExecutor` 的方式来创建。下面是原因：

1. **资源耗尽的风险**：
   - `FixedThreadPool` 和 `SingleThreadPool` 允许的请求队列长度为 `Integer.MAX_VALUE`，可能会堆积大量的请求，导致内存溢出。
   - `CachedThreadPool` 和 `ScheduledThreadPool` 允许的创建线程数量也是 `Integer.MAX_VALUE`，可能会创建大量的线程，同样导致内存溢出。

2. **避免默认实现的问题**：
   - `Executors` 使用的默认线程工厂创建的线程都是非守护线程，且没有设置线程名称和优先级，这在某些应用场景中可能不是最佳选择。
   - 某些由 `Executors` 创建的线程池，如 `newCachedThreadPool`，使用了无限制的任务队列，如果任务提交速度超过线程处理速度，会导致内存溢出风险。

**正确创建线程池的方式**：
避免使用 `Executors` 创建线程池，而是通过直接调用 `ThreadPoolExecutor` 的构造函数来自己创建线程池。在创建的同时，给阻塞队列指定容量，以避免资源耗尽的问题。例如：

```java
private static ExecutorService executor = new ThreadPoolExecutor(
    10, 10, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10)
);
```

这样，一旦提交的线程数超过当前可用线程数，就会抛出 `java.util.concurrent.RejectedExecutionException`，避免了资源耗尽的风险。

总之，使用 `ThreadPoolExecutor` 可以更明确地控制线程池的运行规则，确保系统的稳定性和性能。

## 线程池的配置需要综合考虑的因素

1. **任务性质**：
   - 考虑任务是**CPU密集型**还是**I/O密集型**。
      - 对于CPU密集型任务，应配置较小的线程池，避免过多线程竞争导致性能下降。如：不超过CPU核心数
      - 对于I/O密集型任务，可以配置较大的线程池，充分利用CPU资源。如：CPU核心数 * 2

2. **系统资源**：
   - 硬件资源如**CPU核心数**、**内存大小**等也是设计线程池参数的重要考虑因素。
   - 根据系统资源的实际情况，合理配置线程池的大小。

3. **任务队列**：
   - 选择合适的任务队列类型，例如**有界队列**或**无界队列**。
   - 有界队列可以限制线程池的最大任务数，避免资源耗尽。
   - 无界队列可以适应突发任务，但需要注意内存占用。

4. **线程池大小**：
   - 核心线程数和最大线程数的设置需要根据业务需求和系统资源来决定。
   - 核心线程数控制线程池的基本运行，最大线程数用于处理突发任务。

5. **线程存活时间**：
   - 设置线程的存活时间，即线程在空闲状态下的最长存活时间。
   - 这可以避免线程池中长时间空闲的线程占用资源。

6. **拒绝策略**：
   - 配置线程池的拒绝策略，用于处理无法接受新任务的情况。
   - 常见的拒绝策略包括丢弃任务、抛出异常、阻塞等。

总之，合理配置线程池需要综合考虑任务性质、系统资源、队列类型、线程池大小、线程存活时间和拒绝策略等因素。
