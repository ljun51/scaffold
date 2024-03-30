# CyclicBarrier

**CyclicBarrier**（也称为**同步屏障**）是Java中的一个并发工具类，用于在多个线程之间同步操作。与`CountDownLatch`类似，它也可以用来等待一组线程到达同一个同步点。不同的是，`CyclicBarrier`可以重复使用，而`CountDownLatch`只能使用一次。让我们深入了解一下它的作用和用法：

1. **核心概念**：
    - `CyclicBarrier`允许一组线程互相等待，直到所有线程都到达某个屏障（barrier）点，然后这些线程可以继续执行后续的任务。
    - 这个屏障是可以循环使用的，也就是说，当所有线程都达到屏障点后，屏障会自动重置，等待下一轮的线程到来。

2. **业务场景**：
    - 假设有一个大型电商公司，在每年的“双十一”大促期间，需要进行大量的商品数据预处理工作，以应对即将到来的购物高峰。
    - 这个预处理工作包括很多步骤，比如商品信息的校验、库存的更新、价格的调整等等。
    - 公司可以将整个预处理任务划分为多个子任务，每个子任务由一个独立的线程来完成，但是，这些子任务之间存在一定的依赖关系，比如某个子任务需要等待其他子任务完成后才能开始执行。
    - 在这类场景中，可以使用`CyclicBarrier`实现多个线程之间的等待，将每个子任务的结束点设置为一个屏障点，确保每个阶段的任务都按照预定的顺序执行，同时充分利用多线程的优势，提高处理效率。

3. **技术场景**：
    - `CyclicBarrier`位于`java.util.concurrent`包中，通常用来解决以下几类技术方面的问题：
        - **线程同步**：多个线程需要同时进行某些操作，而这些操作需要在所有线程都准备好之后才能开始。
        - **资源分解与任务划分**：处理大量数据或执行复杂任务时，将任务分解成多个子任务，由不同的线程并行处理。
        - **循环使用**：与`CountDownLatch`不同，`CyclicBarrier`可以重复使用，适用于多轮的任务同步。
        - **异常处理**：当线程在屏障点等待时，如果某个线程因为异常而中断，它可以传播这个异常给其他正在等待的线程。
        - **线程间的协作**：某些场景中，线程之间需要紧密协作，如生产者-消费者模式中的多个消费者线程需要等待所有生产者线程完成生产后才能开始消费。

4. **代码示例**：
   下面是一个使用`CyclicBarrier`的简单示例代码，模拟了一个多线程任务，其中每个线程代表一个工人，他们需要完成各自的工作部分，然后在一个屏障点等待其他工人完成工作，一旦所有工人都完成了工作，他们将一起进行下一个阶段的工作：

   ```java
   import java.util.concurrent.BrokenBarrierException;
   import java.util.concurrent.CyclicBarrier;

   public class CyclicBarrierCase1 {
      public static void main(String[] args) {  
        final int totalWorker = 5; // 工人总数  
        CyclicBarrier cyclicBarrier = new CyclicBarrier(totalWorker); // 创建一个CyclicBarrier实例，并指定总工作线程数  
  
        for (int i = 0; i < totalWorker; i++) {  
            new Thread(() -> {  
                System.out.println("工人" + Thread.currentThread().getId() + "已准备就绪");  
                try {  
                    // 线程在此等待，直到所有线程都达到这个屏障点  
                    cyclicBarrier.await();  
                } catch (InterruptedException | BrokenBarrierException e) {  
                    e.printStackTrace();  
                }  
                System.out.println("工人" + Thread.currentThread().getId() + "开始下一阶段的工作");  
            }).start();  
        }  
      }  
   }
   ```

## 和 CountDownLatch 对比
* CyclicBarrier适用于多个线程在同一点同时执行。
* CyclicBarrier可以重置和重用，而CountDownLatch不可以重用。
* CyclicBarrier利用ReentrantLock的Condition来阻塞和通知线程，而CountDownLatch利用AQS的共享锁和CAS来进行线程的通知。
* CountDownLatch和CyclicBarrier都有让多个线程等待同步然后再开始下一步动作的意思，但是CountDownLatch的下一步的动作实施者是主线程，具有不可重复性；而CyclicBarrier的下一步动作实施者还是“其他线程”本身，具有往复多次实施动作的特点。
