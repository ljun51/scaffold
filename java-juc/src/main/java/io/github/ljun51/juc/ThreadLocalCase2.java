package io.github.ljun51.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * @author lee
 */
public class ThreadLocalCase2 {

    // Atomic integer containing the next thread ID to be assigned
    private static final AtomicInteger nextId = new AtomicInteger(0);

    // Thread local variable containing each thread's ID
    private static final ThreadLocal<Integer> threadId =
            new ThreadLocal<Integer>() {
                @Override
                protected Integer initialValue() {
                    return nextId.getAndIncrement();
                }
            };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }

    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            local.set(Thread.currentThread().getName() + ":" + i + "-" + get());
            System.out.println("线程：" + Thread.currentThread().getName() + ", local: " + local.get());
        }).start());
    }
}
