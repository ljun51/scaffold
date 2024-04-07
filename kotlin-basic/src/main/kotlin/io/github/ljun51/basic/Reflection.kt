package io.github.ljun51.basic

// Todo 函数组合
fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
    return { x -> f(g(x)) }
}

fun isOdd(x: Int) = x % 2 != 0

/*fun main() {
    fun length(s: String) = s.length
    val oddLength = compose(::isOdd, ::length)
    val strings = listOf("a", "ab", "abc", "abcd")
    println(strings.filter(oddLength)) // [a, abc]
}*/

// 属性引用
val x = 1
fun main() {
    println(::x) // val x: kotlin.Int
    println(::x.name) // x
}