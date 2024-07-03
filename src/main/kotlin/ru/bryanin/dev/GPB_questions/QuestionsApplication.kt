package ru.bryanin.dev.questions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuestionsApplication

fun main(args: Array<String>) {
    runApplication<QuestionsApplication>(*args)

    // KotlinVariables
    val kotlinVariables = KotlinVariables()
    // print(kotlinVariables.y) // Error
    kotlinVariables.init()
    println(kotlinVariables.param2) // Ok


    // KotlinClass
    fun testingDataClass() {
        val myDataObject = KotlinDataClass("Параметр 1", 3)
        println(myDataObject)
    }
    val kotlinClass = KotlinClass()
    testingDataClass()

    // Порядок инициализации классов
    val d = KotlinClass.Derived("dgsd", "52345");

}
