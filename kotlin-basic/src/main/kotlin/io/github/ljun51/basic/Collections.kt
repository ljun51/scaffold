package io.github.ljun51.basic

fun main() {
    // Todo kotlin 添加的便利方法
    val names = listOf("Tom", "Jerry")
    println(names.javaClass)

    for ((index, value) in names.withIndex()) {
        println("$index $value")
    }

    // Todo 使用 Pair 和 Triple, Pair用于大小为2的元组，Triple用于大小为3的元组
    println(Pair("Tom", "Jerry"))
    println(mapOf("Tom" to "Cat", "Jerry" to "Mouse"))

    val airportCodes = listOf("LAX", "SFO", "PDX", "SEA")
    val temperatures = airportCodes.map { code -> code to getTemperatureAtAirport(code) }
    for (temp in temperatures) {
        println("Airport: ${temp.first}: Temperature: ${temp.second}")
    }

    // Todo 对象和基元数组
    val friends = arrayOf("Tom", "Jerry", "Mike", "Lucy")
    println(friends::class)
    println(friends.javaClass)
    println("${friends[0]} and ${friends[1]}") // Tom and Jerry

    // Todo 使用列表
    // 要创建一个不可变列表，需要使用listOf()，不可变是隐含的，当有选择的时候，这也是我们的首选。但是如果你真的需要创建一个可变列表，那么使用mutableListOf()
//    val fruits = listOf("Apple", "Banana", "Orange")
//    println(fruits) // [Apple, Banana, Orange]
//    println(fruits.contains("Apple")) // true
//    println("Apple" in fruits) // true
//    fruits.add("Grape") // error
//    val fruits2 = fruits + "Grape"
//    println(fruits2) // [Apple, Banana, Orange, Grape]
//    val fruits3 = fruits - "Apple"
//    println(fruits3) // [Banana, Orange]

//    val fruits = mutableListOf("Apple", "Banana", "Orange")
//    fruits.add("Grape") // [Apple, Banana, Orange, Grape]

    // Todo 使用集合
    val fruits = setOf("Apple", "Banana", "Apple")
    println(fruits) // [Apple, Banana]
    println(fruits::class) // class java.util.LinkedHashSet
    println(fruits.javaClass) // class java.util.LinkedHashSet

    // Todo 使用映射
    val airports = mapOf("LAX" to "Los Angeles", "SFO" to "San Francisco")
    println(airports.size) // 2
    println(airports.containsKey("LAX")) // true
    println(airports.containsValue("San Francisco")) // true
    println("LAX" in airports) // true
//    val lax: String = airports.get("LAX") // ERROR
//    val lax: String? = airports.get("LAX")
//    val lax: String? = airports["LAX"]
    val lax = airports.getOrDefault("LAX", "Los Angeles")
    val airportsExample = airports + ("EX" to "Example")
    println(airportsExample) // {LAX=Los Angeles, SFO=San Francisco, EX=Example}
    for (entry in airports) {
        println("${entry.key} -> ${entry.value}")
    }
    for ((key, value) in airports) {
        println("$key -> $value")
    }
}

fun getTemperatureAtAirport(code: String): String =
        "${Math.round(Math.random() * 30) + code.count()} C"
