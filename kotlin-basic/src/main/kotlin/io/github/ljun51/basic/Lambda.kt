package io.github.ljun51.basic

// 高阶函数
fun <T, R> Collection<T>.fold(
    initial: R,
    combine: (acc: R, nextElement: T) -> R
): R {
    var accumulator: R = initial
    for (element in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

fun main() {
    var items = listOf(1, 2, 3, 4, 5)
    // // Lambdas 表达式是花括号括起来的代码块。
    items.fold(0, {
        // 如果一个 lambda 表达式有参数，前面是参数，后跟“->”
            acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        // lambda 表达式中的最后一个表达式是返回值：
        result
    })

    // lambda 表达式的参数类型是可选的，如果能够推断出来的话：
    val joinedToString = items.fold("Elements:", {acc, i -> acc + " " + i})
    // 函数引用也可以用于高阶函数调用：
    val product = items.fold(1, Int::times)

    println("joinedToString = $joinedToString")
    println("product = $product")
}