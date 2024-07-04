package ru.bryanin.dev.GPB_questions

class KotlinClass {

    open class Base(val name: String) {
        init { println("Initializing a base class") }
        companion object {
            init { println("Initializing a companion object of base class") }
        }
        open val size: Int =
            name.length.also { println("Initializing field of base class") }
    }

    class Child(name: String, val lastName: String) : Base(name.also { println("Argument for the base class constructor") }) {
        init { println("Initializing a child class") }
        companion object {
            init { println("Initializing a companion object of child class") }
        }
        override val size: Int =
            (super.size + lastName.length).also { println("Initializing field of child class") }
    }
}