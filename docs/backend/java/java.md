# Java

## 基础
### [Collection](./java-collection.md)
属于 JCF（Java Collections Framework）成员。Java标准库自带的 `java.util` 包提供了集合类，它是除 Map 外所有其他集合类的根接口。
Java的 `java.util` 包主要提供了以下三种类型的集合：
* List：一种有序列表的集合，例如，按索引排列的Student的List；
* Set：一种保证没有重复元素的集合，例如，所有无重复名称的Student的Set；
* Map：一种通过键值（key-value）查找的映射表集合，例如，根据Student的name查找对应Student的Map。

它们有的是有序的、有的是无序的，有的可以重复、有的不能重复，有的是线程安全、有的线程不安全。

## 并发

### CAS
在 Java 中，**CAS（Compare-and-Swap）** 机制是一种原子操作，用于实现线程安全和无锁并发控制。它通过比较和替换的方式，确保只有一个线程能够成功地更新共享变量的值。

以下是关于 CAS 的一些重要信息：

- **CAS 原理**：
  - CAS 涉及三个参数：内存位置（地址）、期望值和新值。
  - 操作过程如下：
    1. 检查指定内存位置的当前值是否与期望值相等。
    2. 如果相等，将新值写入该内存位置。
    3. 如果不相等，不执行更新操作。
  - CAS 是一种乐观锁的实现方式。

- **CAS 优缺点**：
  - **优点**：
    - 可以保证变量操作的原子性。
    - 在并发度不是很高的情况下，使用 CAS 比使用锁机制效率更高。
    - 适用于对共享资源占用时间较短的情况。
  - **缺点**：
    - 可能会消耗较高的 CPU。
    - 不能保证代码块的原子性。
    - 存在 ABA 问题（解决方法是使用 AtomicStampedReference 类）。

- **应用场景**：
  - CAS 适用于并发度不是很高、对共享资源占用时间较短的情况。
  - 在高并发环境中，如果仍然想通过原子类来更新，可以使用 LongAdder 替代类。

总之，CAS 机制是一种重要的原子操作，用于实现线程安全和无锁并发控制。

### happens-before 原则
在 Java 中，**happens-before** 原则是 Java 内存模型（JMM）中的一个核心概念。理解这个原则对于理解并发编程和多线程环境下的内存可见性和有序性非常重要。

以下是关于 **happens-before** 原则的详细解释：

- **定义**：
  - 如果一个操作 **happens-before** 另一个操作，那么第一个操作的执行结果将对第二个操作可见，而且第一个操作的执行顺序排在第二个操作之前。
  - 这个原则用于保证线程之间的内存可见性和有序性。

- **规则**：
  1. **程序顺序规则**：一个线程中的每个操作，**happens-before** 于该线程中的任意后续操作。
  2. **监视器锁规则**：对一个锁的解锁，**happens-before** 于随后对这个锁的加锁。
  3. **volatile 变量规则**：对一个 volatile 域的写，**happens-before** 于任意后续对这个 volatile 域的读。

- **应用场景**：
  - **happens-before** 原则用于判断并发操作是否安全。
  - 它确保了线程之间的内存可见性，使得在不改变程序执行结果的前提下，尽可能提高程序执行的并行度。

总之，**happens-before** 原则是 Java 内存模型中的关键概念，用于保证线程之间的内存可见性和有序性。

### AQS队列
**AbstractQueuedSynchronizer**（AQS）是Java并发包中的一个核心组件，用于实现锁和同步器。许多我们熟知的并发工具，如`ReentrantLock`、`CountDownLatch`、`Semaphore`等，都借助了AQS来实现。

让我们深入了解一下AQS的设计思想和组成部分：

1. **基础**：
  - AQS定义了一套多线程访问共享资源的同步模板，解决了实现同步器时涉及的大量细节问题。
  - 虽然大多数开发者可能永远不会直接使用AQS来实现自己的同步器，但了解AQS的原理对于架构设计和面试都很有帮助。

2. **组成结构**：
  - AQS由三部分组成：
    - **同步状态（state）**：维护了一个同步状态变量，不同同步器的state语义可以由实现者自定义。
    - **CLH队列**：AQS内部维护的FIFO双向队列，用于管理等待资源的线程。
    - **条件变量（ConditionObject）**：包含Node组成的条件单向队列，用于支持条件等待。

3. **CLH队列**：
  - CLH（Craig, Landin, and Hagersten）队列是AQS内部的FIFO队列。
  - 每个等待资源的线程被封装成一个Node节点，通过CAS原子操作插入队列尾部。
  - CLH队列具有公平性、无锁快速插入等优点。

4. **Node内部类**：
  - Node是AQS的内部类，每个等待资源的线程都对应一个Node节点。
  - Node的waitStatus表示等待状态，nextWaiter用于标记共享式或独占式。
  - Node在CLH队列时，nextWaiter表示下一个节点指针。

总之，AQS是一个用于实现锁和同步器的框架，通过CLH队列管理竞争资源的线程，Node节点是AQS的重要组成部分。

### ReentrantLock
**ReentrantLock**（可重入锁）是Java中的一种同步工具，它提供了比内置`synchronized`关键字更灵活和可定制的锁定机制。让我们深入了解一下ReentrantLock的原理和实现细节：

1. **CAS（Compare and Swap）**：
  - CAS是一种无锁算法，用于实现原子操作。
  - 它有三个操作数：内存值V、旧的预期值A、要修改的新值B。
  - 当且仅当预期值A和内存值V相同时，将内存值V修改为B，否则不做任何操作。
  - 在Java中，CAS主要由`sun.misc.Unsafe`类通过JNI调用CPU底层指令实现。

2. **AQS队列**：
  - AQS（AbstractQueuedSynchronizer）是ReentrantLock的核心组件，用于构建锁和同步容器的框架。
  - AQS使用FIFO的队列（也叫CLH队列）来表示排队等待锁的线程。
  - 队列头节点称作“哨兵节点”或“哑节点”，它不与任何线程关联，其他节点与等待线程关联。

3. **ReentrantLock的流程**：
  - ReentrantLock首先通过CAS尝试获取锁，如果锁已被占用，线程加入AQS队列并等待。
  - 当前驱线程的锁被释放后，挂在CLH队列为首的线程被唤醒，然后继续CAS尝试获取锁。
  - 非公平锁允许其他线程抢占，而公平锁只有队头的线程可以获取锁。

4. **lock() 和 unlock() 的实现**：
  - `lock()`函数：
    - 非公平锁：如果成功通过CAS修改了state，线程标志自己成功获取锁；否则线程加入AQS队列并等待。
    - 公平锁：直接进入等待队列。
  - `tryAcquire()`函数：
    - 检查state字段，若为0，尝试占用；若不为0，检查当前锁是否被自己占用，若是则更新state表示重入锁次数。

总之，ReentrantLock提供了更灵活的锁控制，支持公平锁和非公平锁，且可以显式地加锁和解锁。

### JUC
### [线程](./java-thread.md)
### 线程池
#### [FutureTask](./java-futuretask.md)
#### ThreadPoolExecutor(./java-threadpoolexecutor.md)
#### ScheduledThreadPoolExecutor
#### Fork/Join
Fork/Join框架是Java并发工具包中的一种可以将一个大任务拆分为很多小任务来异步执行的工具，自JDK1.7引入。
#### [CountDownLatch](./java-countdownlatch.md)
#### CyclicBarrier
### 锁
#### [Synchronized 关键字](./java-synchronized.md)
#### volatile 关键字
#### final 关键字

## [JVM](./java-jvm.md)
### Hotspot (JDK8默认)
HotSpot 是一种 CMS 算法实现的垃圾收集器。它非常符合在注重用户体验的应用上使用，它是 HotSpot 虚拟机第一款真正意义上的并发收集器，
它第一次实现了让垃圾收集线程与用户线程（基本上）同时工作 。从名字（ Mark Sweep ）上也可以看出 CMS收集器是一种 “标记-清除”算法实现的垃圾收集器。
### G1 (JDK11默认)
### ZGC

## Java版本新特性
### Java 11
### Java 17
### Java 21