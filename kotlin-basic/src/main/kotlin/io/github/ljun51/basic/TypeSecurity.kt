package io.github.ljun51.basic

fun main() {
    // Todo Any, Nothing

    // Todo 安全调用运算符
    /*
    if (name != null) {
        println(name.reversed())
    }
    return null
    // name?.reversed()
    // name?.reversed()?.toUpperCase()
    */

    // Todo Elvis 运算符
    /*
    val result = name?.reversed()?.toUpperCase()
    return if (result == null) "Joker" else result
    return name?.reversed()?.toUpperCase() ?: "Joker"
    */

    // Todo 不要使用不安全的断言运算符
    /*
    val result = name!!.reversed()
    return result
    */

    // Todo 类型检查 is !is
    val obj: Any = "Hello"
    if (obj is String) {
        print(obj.length)
    }

    if (obj !is String) { // 与 !(obj is String) 相同
        print("Not a String")
    } else {
        print(obj.length)
    }

    // Todo "不安全的"类型转换 as as?
    val y: Any = "Hello"
    val x: String = y as String
    val x1: String? = y as String?
    // 为了避免异常，可以使用安全转换操作符 as?，它可以在失败时返回 null
    val x2: String? = y as? String

    // Todo 泛型
    // 协变：在 Kotlin 中，协变是指在类型参数的继承关系中，允许将一个泛型类型的子类型赋值给父类型。
// 我们可以使用 out 关键字来标记类型参数为协变。这样一来，我们就可以安全地将一个泛型类型的子类型赋值给父类型。
//    协变 (Covariance)：List<Dog> 是 List<Animal> 的子类型。
//    逆变 (Contravariance)：List<Animal> 是 List<Dog> 的子类型。
//    抗变 (Invariant)：List<Animal> 与 List<Dog> 没有任何继承关系。
    open class Animal {
    }

    class Dog : Animal() {
    }

    val animals: List<Animal> = listOf(Dog(), Animal())
    val dogs: List<Dog> = listOf(Dog())
    // 逆变
    // where 类型约束
    // 星投影 用<*>定义参数类型，它是指定泛型只读类型和原始类型的Kotlin等效物。
}