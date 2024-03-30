# Semaphore

在Java中，**Semaphore**（信号量）用于控制同时访问特定资源的线程数量，通过协调各个线程，以保证合理地使用资源。让我们深入了解一下Semaphore的用法和原理：

1. **Semaphore的作用**：
    - 在Java中，使用了`synchronized`关键字和`Lock`锁实现了资源的并发访问控制，在同一时间只允许唯一的线程进入临界区访问资源（读锁除外）。这样的控制主要目的是为了解决多个线程并发访问同一资源造成数据不一致的问题。
    - 另一种场景下，一个资源有多个副本可供同时使用，比如打印机房有多个打印机、厕所有多个坑可供同时使用。在这种情况下，Java提供了另一种并发访问控制方式——资源的多副本并发访问控制，其中之一就是信号量（Semaphore）。

   2. **Semaphore的实现原理**：
       - Semaphore用来保护一个或多个共享资源的访问。它内部维护了一个计数器，表示可以访问的共享资源的个数。
       - 当一个线程要访问共享资源时，先获得信号量。如果信号量的计数器值大于1，意味着有共享资源可以访问，线程将使计数器值减1，然后访问资源。如果计数器值为0，线程进入休眠。
       - 当某个线程使用完共享资源后，释放信号量，并将信号量内部的计数器加1。之前进入休眠的线程将被唤醒并再次试图获得信号量。

      3. **Semaphore的使用**：
          - 在使用Semaphore时，首先需要构建一个参数来指定共享资源的数量。构造完成后，可以获取Semaphore，使用完资源后再释放Semaphore。
          - 下面的代码模拟了控制商场厕所的并发使用：

            ```java
            import java.util.concurrent.Semaphore;
 
            public class ResourceManage {
                private final Semaphore semaphore;
                private boolean[] resourceArray;
 
                public ResourceManage() {
                    this.resourceArray = new boolean[10]; // 存放厕所状态
                    this.semaphore = new Semaphore(10, true); // 控制10个共享资源的使用，使用先进先出的公平模式进行共享
                    for (int i = 0; i < 10; i++) {
                        resourceArray[i] = true; // 初始化为资源可用的情况
                    }
                }
 
                public void useResource(int userId) {
                    semaphore.acquire();
                    try {
                        int id = getResourceId(); // 占到一个坑
                        System.out.println("userId:" + userId + "正在使用资源，资源id:" + id);
                        Thread.sleep(100); // 模拟使用资源
                        resourceArray[id] = true; // 退出这个坑
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.release(); // 释放信号量，计数器加1
                    }
                }
 
                private int getResourceId() {
                    int id = -1;
                    for (int i = 0; i < 10; i++) {
                        if (resourceArray[i]) {
                            resourceArray[i] = false;
                            id = i;
                            break;
                        }
                    }
                    return id;
                }
            }
 
            public class ResourceUser implements Runnable {
                private ResourceManage resourceManage;
                private int userId;
 
                public ResourceUser(ResourceManage resourceManage, int userId) {
                    this.resourceManage = resourceManage;
                    this.userId = userId;
                }
 
                public void run() {
                    System.out.println("userId:" + userId + "准备使用资源...");
                    resourceManage.useResource(userId);
                    System.out.println("userId:" + userId + "使用资源完毕...");
                }
 
                public static void main(String[] args) {
              SemaphoreCase1 resourceManage = new SemaphoreCase1();
              Thread[] threads = new Thread[100];
              for (int i = 0; i < 100; i++) {
                  Thread thread = new Thread(new ResourceUser(resourceManage, i));//创建多个资源使用者
                  threads[i] = thread;
              }
              for (int i = 0; i < 100; i++) {
                  Thread thread = threads[i];
                  try {
                      thread.start();//启动线程
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
            }
      ```

## 场景问题 
* semaphore初始化有10个令牌，11个线程同时各调用1次acquire方法，会发生什么?
答案：拿不到令牌的线程阻塞，不会继续往下运行。

* semaphore初始化有10个令牌，一个线程重复调用11次acquire方法，会发生什么?
答案：线程阻塞，不会继续往下运行。可能你会考虑类似于锁的重入的问题，很好，但是，令牌没有重入的概念。你只要调用一次acquire方法，就需要有一个令牌才能继续运行。

* semaphore初始化有1个令牌，1个线程调用一次acquire方法，然后调用两次release方法，之后另外一个线程调用acquire(2)方法，此线程能够获取到足够的令牌并继续运行吗?
答案：能，原因是release方法会添加令牌，并不会以初始化的大小为准。

* semaphore初始化有2个令牌，一个线程调用1次release方法，然后一次性获取3个令牌，会获取到吗?
答案：能，原因是release会添加令牌，并不会以初始化的大小为准。Semaphore中release方法的调用并没有限制要在acquire后调用。
