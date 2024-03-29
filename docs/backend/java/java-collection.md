# Collection
属于 JCF（Java Collections Framework）成员。Java标准库自带的 `java.util` 包提供了集合类，它是除 Map 外所有其他集合类的根接口。
Java的 `java.util` 包主要提供了以下三种类型的集合：
* List：一种有序列表的集合，例如，按索引排列的Student的List；
* Set：一种保证没有重复元素的集合，例如，所有无重复名称的Student的Set；
* Map：一种通过键值（key-value）查找的映射表集合，例如，根据Student的name查找对应Student的Map。

它们有的是有序的、有的是无序的，有的可以重复、有的不能重复，有的是线程安全、有的线程不安全。

Java集合的设计有几个特点：
一是实现了接口和实现类相分离，例如，有序表的接口是List，具体的实现类有ArrayList，LinkedList等；
二是支持泛型，我们可以限制在一个集合中只能放入同一种数据类型的元素，例如：
```java
List<String> list = new ArrayList<>(); // 只能放入String类型
```
最后，Java 访问集合总是通过统一的方式——迭代器（Iterator）来实现，它最明显的好处在于无需知道集合内部元素是按什么方式存储的。

由于 Java 的集合设计非常久远，中间经历过大规模改进，我们要注意到有一小部分集合类是遗留类，不应该继续使用：
* Hashtable：一种线程安全的Map实现；
* Vector：一种线程安全的List实现；
* Stack：基于Vector实现的LIFO的栈。

还有一小部分接口是遗留接口，也不应该继续使用：
* Enumeration<E>：已被Iterator<E>取代。

## List
1. 是一种有序集合
2. 允许存储重复元素
3. 有索引，可以使用普通的 for 循环遍历

* ArrayList
* LinkedList
* Vector

## Set
1. 不允许存储重复元素
2. 没有索引，不可以使用普通的 for 循环遍历

* ArraySet
* HashSet 无序
* TreeSet 无序
* LinkedHashSet 有序
* SortedSet 有序

## Map
* HashMap 无序、线程不安全。最常用的Map，它根据键的 HashCode 值存储数据，根据键可以直接获取它的值，具有很快的访问速度。
HashMap最多只允许一条记录的键为 Null(多条会覆盖)；允许多条记录的值为 Null。
* TreeMap 有序、线程不安全。能够把它保存的记录根据键排序，默认是按升序排序，也可以指定排序的比较器，当用 Iterator 遍历 TreeMap 时，得到的记录是排过序的。TreeMap 不允许键的值为 Null。
* HashTable 线程安全。与 HashMap 类似，不同的是 key 和 value 的值均不允许为 Null；它支持线程的同步，即任一时刻只有一个线程能写 Hashtable，因此也导致了 Hashtale 在写入时会比较慢。 
* SortedMap
* LinkedHashMap 
* WeakHashMap
* ConcurrentHashMap