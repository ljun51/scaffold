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
    // Todo 类型转换 as as?
    // Todo 泛型
    // 协变
    // 逆变
    // where 类型约束
    // 星投影
}