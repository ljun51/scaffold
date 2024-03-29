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

### JUC
### [线程](./java-thread.md)
### 线程池
#### FutureTask
#### ThreadPoolExecutor
#### ScheduledThreadPoolExecutor
#### Fork/Join
#### CountDownLatch
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