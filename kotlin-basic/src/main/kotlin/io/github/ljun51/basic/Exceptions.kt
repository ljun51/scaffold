package io.github.ljun51.basic

fun main() {
    // 使用 throw 表达式来抛出异常
//    throw Exception("Hi there")

    // 使用 try……catch 表达式来捕获异常
    try {
        // 一些代码
    } catch (e: Exception) {
        // 处理程序
    } finally {
        // 可选的 finally 块
    }

    // Try 是一个表达式
//    val a: Int? = try { input.toInt() } catch (e: NumberFormatException) { null }
}