#### Synchronized 关键字
在使用 `sychronized` 关键字时需要把握如下注意点：
* 一把锁只能同时被一个线程获取，没有获得锁的线程只能等待；
* 每个实例都对应有自己的一把锁（`this`）,不同实例之间互不影响；
**例外：锁对象是`*.class`以及 `synchronized` 修饰的是 `static` 方法的时候，所有对象公用同一把锁；**
* `synchronized` 修饰的方法，无论方法正常执行完毕还是抛出异常，都会释放锁。

对象锁
包括方法锁（默认锁对象为this，当前实例对象）和同步代码块锁（自己指定锁对象）

代码块形式：手动指定锁定对象，可以是this，也可以是自定义的锁

示例1
```java
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
```
输出结果：
```
21:08:26.548 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock1 - 我是线程Thread-0
21:08:29.551 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock1 - Thread-0结束
21:08:29.551 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock1 - 我是线程Thread-1
21:08:32.552 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock1 - Thread-1结束
```

示例2
```java
/**
 * 手动指定锁定对象：自定义的锁
 *
 * @author lee
 */
@Slf4j
public class SynchronizedObjectLock2 implements Runnable {

    static SynchronizedObjectLock2 instance = new SynchronizedObjectLock2();
    // 创建2把锁
    Object block1 = new Object();
    Object block2 = new Object();

    @Override
    public void run() {
        // 这个代码块使用的是第一把锁，当他释放后，后面的代码块由于使用的是第二把锁，因此可以马上执行
        synchronized (block1) {
            log.info("block1锁,我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("block1锁," + Thread.currentThread().getName() + "结束");
        }

        synchronized (block2) {
            log.info("block2锁,我是线程" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("block2锁," + Thread.currentThread().getName() + "结束");
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
    }
}
```
输出结果：
```
21:12:18.898 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block1锁,我是线程Thread-0
21:12:21.908 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block1锁,Thread-0结束
21:12:21.908 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block2锁,我是线程Thread-0
// 可以看到当第一个线程在执行完第一段同步代码块之后，第二个同步代码块可以马上得到执行，因为他们使用的锁不是同一把
21:12:21.908 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block1锁,我是线程Thread-1
21:12:24.909 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block1锁,Thread-1结束
21:12:24.909 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block2锁,Thread-0结束
21:12:24.909 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block2锁,我是线程Thread-1
21:12:27.910 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock2 - block2锁,Thread-1结束
```

方法锁形式：synchronized修饰普通方法，锁对象默认为this
```java
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
```
输出结果：
```
21:16:21.386 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock3 - 我是线程Thread-0
21:16:24.389 [Thread-0] INFO io.github.ljun51.sync.SynchronizedObjectLock3 - Thread-0结束
21:16:24.390 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock3 - 我是线程Thread-1
21:16:27.390 [Thread-1] INFO io.github.ljun51.sync.SynchronizedObjectLock3 - Thread-1结束
```

synchronized的缺陷
* 效率低：锁的释放情况少，只有代码执行完毕或者异常结束才会释放锁；试图获取锁的时候不能设定超时，不能中断一个正在使用锁的线程，相对而言，Lock可以中断和设置超时；
* 不够灵活：加锁和释放的时机单一，每个锁仅有一个单一的条件(某个对象)，相对而言，读写锁更加灵活;
* 无法知道是否成功获得锁：相对而言，Lock可以拿到状态，如果成功获取锁，....，如果获取失败，.....

Lock解决相应问题，Lock类这里不做过多解释，主要看里面的4个方法:
* lock(): 加锁
* unlock(): 解锁
* tryLock(): 尝试获取锁，返回一个boolean值
* tryLock(long,TimeUtil): 尝试获取锁，可以设置超时

Synchronized加锁只与一个条件(是否获取锁)相关联，不灵活，后来Condition与Lock的结合解决了这个问题。

synchronized是通过软件(JVM)实现的，简单易用，即使在JDK5之后有了Lock，仍然被广泛的使用。使用Synchronized有哪些要注意的？
* 锁对象不能为空，因为锁的信息都保存在对象头里
* 作用域不宜过大，影响程序执行的速度，控制范围过大，编写代码也容易出错
* 避免死锁
* 在能选择的情况下，既不要用Lock也不要用synchronized关键字，用java.util.concurrent包中的各种各样的类，如果不用该包下的类，在满足业务的情况下，可以使用synchronized关键，因为代码量少，避免出错

synchronized是公平锁吗？synchronized实际上是非公平的，新来的线程有可能立即获得监视器，而在等待区中等候已久的线程可能再次等待，这样有利于提高性能，但是也可能会导致饥饿现象。
