# ThreadLocal

**ThreadLocal** 提供线程局部变量，用于在多线程环境下管理线程私有数据。这些变量与普通变量的不同之处在于，访问一个变量的每个线程（通过其 get 或 set 方法）都有自己的、独立初始化的变量副本。ThreadLocal 实例通常是希望将状态与线程（例如，用户 ID 或事务 ID）关联的类中的私有静态字段。例如，下面的类生成每个线程本地的唯一标识符。线程的 id 在首次调用 ThreadId.get（） 时分配，并在后续调用中保持不变。

1. **ThreadLocal 是什么？**
    - `ThreadLocal` 可以理解为线程级别的变量存储。
    - 每个线程都有自己独立的 `ThreadLocal` 副本，其他线程无法直接访问。
    - 常用场景：
        - 对象跨层传递时，避免多次传递，打破层次间的约束。
        - 线程间数据隔离。
        - 存储线程事务信息。
        - 管理数据库连接、Session 会话等。

2. **ThreadLocal 怎么用？**
    - 示例代码：
      ```java
      public class ThreadLocalTest {
          public static void main(String[] args) {
              ThreadLocal<String> local = new ThreadLocal<>();
              IntStream.range(0, 10).forEach(i -> new Thread(() -> {
                  local.set(Thread.currentThread().getName() + ":" + i);
                  System.out.println("线程：" + Thread.currentThread().getName() + ", local: " + local.get());
              }).start());
          }
      }
      ```
    - 输出结果：
      ```
      线程：Thread-3, local: Thread-3:3
      线程：Thread-6, local: Thread-6:6
      线程：Thread-8, local: Thread-8:8
      线程：Thread-1, local: Thread-1:1
      线程：Thread-7, local: Thread-7:7
      线程：Thread-5, local: Thread-5:5
      线程：Thread-2, local: Thread-2:2
      线程：Thread-0, local: Thread-0:0
      线程：Thread-9, local: Thread-9:9
      线程：Thread-4, local: Thread-4:4
      ```
    - 每个线程都有自己的 `local` 值，这是 `ThreadLocal` 的基本使用方式。

3. **ThreadLocal 源码分析**：
    - `ThreadLocal` 内部使用 `ThreadLocalMap` 存储数据。
    - `ThreadLocalMap` 是 `ThreadLocal` 的静态内部类，使用 `Entry` 来保存数据。
    - `Entry` 继承自弱引用，使用 `ThreadLocal` 作为 key，我们设置的值作为 value。

总之，`ThreadLocal` 是一个强大的工具，用于管理线程私有数据，确保线程安全。
