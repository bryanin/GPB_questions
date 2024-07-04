package ru.bryanin.dev.GPB_questions

class Functions {

    fun mapAndLetFunctions() {
        val numbers = mutableListOf("one", "two", "three", "four", "five")
        println(numbers)
        val resultList1 = numbers.map { it.length }.filter { it > 3 }
        println(resultList1)
        val resultList2 = numbers.filter { it.length > 3 }
        println(resultList2)
    }
}