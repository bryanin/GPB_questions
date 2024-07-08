package ru.bryanin.dev.questions

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux
import reactivefeign.spring.config.EnableReactiveFeignClients
import ru.bryanin.dev.GPB_questions.web.nonblocking.service.ReactiveCommentService

@SpringBootApplication
@EnableWebFlux
@EnableReactiveFeignClients
class QuestionsApplication

fun main(args: Array<String>) {
    runApplication<QuestionsApplication>(*args)

//    // KotlinVariables
//    println("KotlinVariables")
//    val kotlinVariables = KotlinVariables()
//    // print(kotlinVariables.y) // Error
//    kotlinVariables.init()
//    println(kotlinVariables.param2) // Ok
//
//
//    // KotlinClass
//    println("KotlinClass")
//    fun testingDataClass() {
//        val myDataObject = KotlinDataClass("Параметр 1", 3)
//        println(myDataObject)
//    }
//    val kotlinClass = KotlinClass()
//    testingDataClass()
//
//    // Порядок инициализации классов
//    println("Порядок инициализации классов")
//    val d = KotlinClass.Child("dgsd", "52345");
//
//    // Null safety
//    println("Null safety")
//    var b: String? = "abc" // can be set to null
//    b = null // ok
//    println(b)
//    var l = if (b != null) b.length else -1
//    println(l)
//    b = "null" // ok
//    println(b)
//    l = if (b != null) b.length else -1
//    println(l)
//
//    // Safe calls
//    println("Safe calls")
//    val a1 = "Kotlin"
//    val b1: String? = null
//    println(b1?.length)
//
//    // Functions
//    println("Functions")
//    Functions().mapAndLetFunctions()



    val response = ReactiveCommentService().getAllComments()
    println(response)

}
