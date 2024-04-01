package io.github.ljun51.concurrent;

import lombok.extern.slf4j.Slf4j;

/**
 * 手动指定锁定对象：this
 *
 * @author lee
 */
@Slf4j
public class SynchronizedObjectLock1 implements Runnable {

    static SynchronizedObjectLock1 instance = new SynchronizedObjectLock1();

    @Override
    public void run() {
        // 同步代码块形式——锁为this,两个线程使用的锁是一样的,线程1必须要等到线程0释放了该锁后，才能执行
        synchronized (this) {
            log.info("我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
