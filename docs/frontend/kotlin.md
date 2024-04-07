# Kotlin

## Ktor
Ktor 是一个使用强大的Kotlin 语言在互联系统中构建异步服务器与客户端的框架。

## 委托
委托类： 委托的是接口的方法
委托属性： 委托的是属性的getter和setter方法
自定义委托： 委托的是自定义的委托类 

## 作用域函数
在 **Kotlin** 中，我们有几个有用的作用域函数，它们可以帮助我们更方便地操作对象。让我们来看看 `also()`、`apply()`、`let()` 和 `run()` 的用法和区别：

1. **`also()`**：
- `also()` 是一个扩展函数，它允许我们在对象上执行一些操作，并返回对象本身。
- 在函数块内，我们可以使用 `it` 来引用该对象。
- 通常用于链式调用，例如初始化对象属性或记录日志。
- 示例：
```kotlin
val person = Person("Alice", 30)
person.also {
it.age = 31
println("Updated age: ${it.age}")
}
```

2. **`apply()`**：
- `apply()` 也是一个扩展函数，它与 `also()` 类似，但返回的是传入的对象本身。
- 常用于对象初始化时对属性进行赋值。
- 示例：
```kotlin
val person = Person("Bob", 25)
person.apply {
age = 26
eat()
work(8)
}
```

3. **`let()`**：
- `let()` 是一个扩展函数，用于对对象进行转换或执行需要对象的操作。
- 在函数块内，我们可以使用 `it` 来引用该对象。
- 返回值为函数块的最后一行或指定的 `return` 表达式。
- 示例：
```kotlin
val person: Person? = Person("Charlie", 28)
val result = person?.let {
it.age = 29
it.eat()
it.work(8)
}
println("Result: $result")
```

4. **`run()`**：
- `run()` 是 `with()` 和 `let()` 的结合体。
- 可以直接在函数块中使用 `this` 来引用该对象，也可以对对象进行统一的判空处理。
- 示例：
```kotlin
val person: Person? = Person("David", 22)
val result = person?.run {
age = 23
eat()
work(8)
}
println("Result: $result")
```
