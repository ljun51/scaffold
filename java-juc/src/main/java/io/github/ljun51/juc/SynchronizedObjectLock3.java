package io.github.ljun51.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 方法锁形式：synchronized修饰普通方法，锁对象默认为this
 *
 * @author lee
 */
@Slf4j
public class SynchronizedObjectLock3 implements Runnable {
    static SynchronizedObjectLock3 instance = new SynchronizedObjectLock3();

    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        log.info("我是线程" + Thread.currentThread().getName());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info(Thread.currentThread().getName() + "结束");
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
