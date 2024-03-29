# 线程
Thread 的状态，一个线程可以是下列状态的一种：
* `NEW`：线程未启动时的状态。
* `RUNNABLE`：正在JVM中执行的状态。
* `BLOCKED`：等待监视器锁而被阻塞的线程就处于这种状态。
* `WAITING`：无限期等待另一个线程执行特定操作的线程处于此状态。
* `TIMED_WAITING`：等待另一个线程执行操作的线程最多在指定的等待时间内处于此状态。
* `TERMINALTED`：已退出的线程处于此状态。

以下是关系到线程运行状态的几个方法：
1. start()：用来启动一个线程。
2. run()：不需要用户调用。当通过start方法启动一个线程之后，当线程获得了CPU执行时间，便进入run方法体去执行具体的任务。
注意，继承Thread类必须重写run方法，在run方法中定义具体要执行的任务。
3. sleep()：sleep方法有两个重载版本：
```java
sleep(long millis)     //参数为毫秒

sleep(long millis,int nanos)    //第一参数为毫秒，第二个参数为纳秒
```
sleep相当于让线程睡眠，交出CPU，让CPU去执行其他的任务。但是有一点要非常注意，sleep方法不会释放锁。
也就是说如果当前线程持有对某个对象的锁，则即使调用sleep方法，其他线程也无法访问这个对象。
4. yield()：调用yield方法会让当前线程交出CPU权限，让CPU去执行其他的线程。它跟sleep方法类似，同样不会释放锁。
但是yield不能控制具体的交出CPU的时间，另外，yield方法只能让拥有相同优先级的线程有获取CPU执行时间的机会
注意，调用yield方法并不会让线程进入阻塞状态，而是让线程重回就绪状态，它只需要等待重新获取CPU执行时间，这一点是和sleep方法不一样的。
5. join()：join方法有三个重载版本：
```java
join()

join(long millis)     //参数为毫秒

join(long millis, int nanos)    //第一参数为毫秒，第二个参数为纳秒
```
假如在main线程中，调用thread.join方法，则main方法会等待thread线程执行完毕或者等待一定的时间。
如果调用的是无参join方法，则等待thread执行完毕，如果调用的是指定了时间参数的join方法，则等待一定的时间。
6. interrupt()：interrupt，顾名思义，即中断的意思。单独调用interrupt方法可以使得处于阻塞状态的线程抛出一个异常，
也就是说，它可以用来中断一个正处于阻塞状态的线程；另外，通过interrupt方法和isInterrupted()方法来停止正在运行的线程。
7. stop()：stop方法在JDK8已经是一个废弃的方法，它是一个不安全的方法。因为调用stop方法会直接终止run方法的调用，并且会抛出一个ThreadDeath错误，
如果线程持有某个对象锁的话，会完全释放锁，导致对象状态不一致。所以stop方法基本是不会被用到的。
8. destroy()：destroy方法在JDK8也是废弃的方法。基本不会被使用到。 

## sleep 和 wait 的区别
基本差别
* sleep是Thread类的方法，wait是Object类中定义的方法
* sleep方法可以在任何的地方使用
* wait方法只能在sychronized方法或synchronized块中使用

最主要的本质区别
* Thread.sleep()只会让出CPU，不会导致锁行为的改变
* Object.wait()不仅会让出CPU，还会释放已经占有的同步资源