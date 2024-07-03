package ru.bryanin.dev.questions

class KotlinVariables {
    // Initializes the variable x at the moment of declaration; type is not required
    // val x = 5
    // Declares the variable c without initialization; type is required
    // val c: Int
    // Initializes the variable c after declaration
    // c = 3
    // 5
    // 3
    // Так не получается, компилятор ругается
    val param1: String by lazy {"by lazy"} // Можно так, например
    val param2: String by lazy { lazyFunction() } // Можно еще функцию в блоке вызвать
    private fun lazyFunction(): String = "Privet"
    // val param3: String // В примере так показано, но не работает здесь
    lateinit var param4: String // Только так
    fun init() {
        param4 = "lateinit"
    }

}