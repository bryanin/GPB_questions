# Вопросы для подготовки

___
### Содержание
* [Kotlin](#Kotlin)
* [Spring](#Spring)
* [MQ](#MQ)
* [ProjectReactor](#ProjectReactor)
___
## <a id="Kotlin">Kotlin</a>
### 1. Инициализация переменных, отличие var от val, lateinit, by lazy

[Официальная документация](https://kotlinlang.org/docs/basic-syntax.html#variables)
   
Получается следующее:
   * val - immutable-переменная, аналог final в java, используется для наименования read-only переменных
   * var - аналог mutable-переменной в java
   * Если к val добавить const, то такая переменная становится константой времени компиляции. Const 'val' are only allowed on top level, in named objects, or in companion objects

Из документации: "You can use variables only after initializing them. You can either initialize a variable 
at the moment of declaration or declare a variable first and initialize it later. In the second case, 
you must specify the data type:"

```
Пример из официальной документации: 
```kotlin
// Initializes the variable x at the moment of declaration; type is not required
val x = 5
// Declares the variable c without initialization; type is required
val c: Int
// Initializes the variable c after declaration 
c = 3
```
> У меня так не получилось, ругается компилятор, см. файл [KotlinVariables.kt](../src/main/kotlin/ru/bryanin/dev/GPB_questions/KotlinVariables.kt) - в чем ошибка?

Что касается lateinit, by lazy (своими словами):
* lateinit применяется с var в случаях, когда нужна инициализация var после объявления переменной. 
Обычно производится через стороннюю функцию или Dependency Injection
* by lazy применяется с val в случаях, когда нужна инициализация val после объявления переменной
> Хотелось бы подробнее узнать, в каких случаях такой подход применяют, когда это хорошо, когда плохо и т.д.

### 2. Разница в классах. Data class, sealed class, object, companion object

[Официальная документация](https://kotlinlang.org/docs/basic-syntax.html#creating-classes-and-instances)
   
Дополнительные материалы:
* [Data class](https://habr.com/ru/articles/752450/#5)
* [Sealed class](https://habr.com/ru/articles/752450/#6)
* [object и companion object](https://bimlibik.github.io/posts/kotlin-object-keyword/)

#### Особенности Data class:
* Data class должен принимать как минимум один параметр в конструктор. В теле класса можно добавить переменные
> "В тело класса можно добавить переменные" - какой смысл? 
* От Data class нельзя наследоваться, так как он является final
> Я почитал на тему open классов и понял, что по умолчанию все классы в Kotlin являются final, и если нужно разрешить наследование от них, добавляют модификатор open. Верно?
* Data class может быть унаследован от других open-классов
* Все параметры основного конструктора должны быть отмечены val или var
> Не понял, почему при добавлении переменных val компилятор их подчеркивает и говорит "public final var param2: Int".
> При этом так называть переменные в других классах я не могу... Компилятор подчеркивает, но не ругается
* Data class не может быть abstract, open, sealed или inner
* Data-классы в Kotlin автоматически генерируют следующие стандартные методы (equals(), hashCode(), toString(), copy(), componentN())

#### Особенности Sealed class (из [статьи](https://habr.com/ru/articles/728742/#%D0%A7%D1%82%D0%BE%20%D1%82%D0%B0%D0%BA%D0%BE%D0%B5%20sealed%20%D0%BA%D0%BB%D0%B0%D1%81%D1%81%20(%D0%B8%D0%B7%D0%BE%D0%BB%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%BD%D1%8B%D0%B9)?)):
* Sealed class - это абстрактный класс, ограничивающий набор подклассов, которые могут быть унаследованы от него
> Было бы интересно узнать, в каких случаях вообще Sealed классы используются
* У sealed класса могут быть наследники, но все они должны находиться в одном пакете с изолированным классом. Изолированный класс "открыт" для наследования по умолчанию, указывать слово open не требуется
* Наследники sealed класса могут быть классами любого типа: data class, объектом, обычным классом, другим sealed классом. Классы, которые расширяют наследников sealed класса могут находиться где угодно
* Изолированные классы абстрактны и могут содержать в себе абстрактные компоненты. Изолированные классы нельзя инициализировать
> Пока читал статью, обнаружил конструкцию "return when is", по аналогии с которой, как я понял, сделали "return switch case"

#### Особенности object и companion object
Анонимный класс — это класс, которые явно не объявлен с помощью class, наследуется от заданного класса или реализует заданный интерфейс. Экземпляры анонимных классов называют анонимными объектами, потому что они объявляются выражением, а не именем. Анонимный объект начинается с ключевого слова object:
```kotlin
val obj = object : SuperClassOrInterface() {
    // implementation here
}
```
Ключевое слово object позволяет одновременно объявить класс и создать его экземпляр (т.е. объект):
* object Name — это объявление объекта (оbject declaration), реализация паттерна Singleton. Объекты можно объявлять внутри класса, при этом нет каких-либо ограничений по их количеству
* companion object — это объект-компаньон внутри класса (также Singleton). Только один объект можно пометить ключевым словом companion object в рамках одного класса
* object — это объект-выражение (анонимный объект/object expression), не Singleton. Объекты можно объявлять внутри класса, при этом нет каких-либо ограничений по их количеству
Пример создания companion object
```kotlin
class MyClass {
  init {
    // Выполняется всегда после инициализации companion object
  }
  companion object {
    init {
      // Выполняется всегда перед блоком init содержащего класса
    }
  }
}
val myClass = MyClass()
```


### 3. Порядок при инициализации классов
[Официальная документация](https://kotlinlang.org/docs/classes.html)

Своими словами о классах в Kotlin:
* Все классы в Kotlin по умолчанию final и наследуются от класса Any
* Если нужен класс, от которого нужно наследоваться, добавляется модификатор доступа open
* Если у дочернего класса есть основной конструктор, то в основной конструктор дочернего класса передаются параметры родительского класса. Если же у дочернего класса нет основного конструктора, каждый объявленный вторичный конструктор должен через super передавать параметры в родительский класс
* При переопределении функций в дочернем классе добавляется override (как ключевое слово, а не как аннотация в Java). Чтобы запретить переопределение, указать ключевое слово final

Что касается порядка инициализации, [официальная документация](https://kotlinlang.org/docs/inheritance.html#derived-class-initialization-order)

Какие выводы я делаю: 
1. Вызывается основной конструктор дочернего класса
2. Вызывается основной конструктор родительского класса
3. Инициализируются companion object родительского класса
4. Инициализируются статические блоки (init) родительского класса
5. Инициализируются поля и методы родительского класса
6. Инициализируются companion object дочернего класса
7. Инициализируются статические блоки (init) дочернего класса
8. Инициализируются поля и методы дочернего класса

Какой результат я получил при самостоятельной проверке [KotlinClass](../src/main/kotlin/ru/bryanin/dev/GPB_questions/KotlinClass.kt)
1. Initializing a companion object of base class
2. Initializing a companion object of child class
3. Argument for the base class constructor
4. Initializing a base class
5. Initializing field of base class
6. Initializing a child class
7. Initializing field of child class
> Верно? Я находил и другие вариации

### 4. Null safely парадигма котлина. Элвис оператор «?:»
[Официальная документация](https://kotlinlang.org/docs/null-safety.html)
Что я понял:
* Если добавить знак ? после типа при объявлении переменных, то такая переменная может содержать null, в противном случае - нет (код не скомпилируется)
* Вот пример, как обратиться к свойству такой nullable-переменной:
```kotlin
val l = if (b != null) b.length else -1
```
* Также можно безопасно обращаться, добавляя знак ? после переменной перед вызовом метода или обращению к свойству:
```kotlin
b?.length
```
"Safe calls are useful in chains"
```kotlin
bob?.department?.head?.name
```
* Еще есть let, обращаясь к которому можно игнорировать null:
```kotlin
val listWithNulls: List<String?> = listOf("Kotlin", null)
for (item in listWithNulls) {
    item?.let { println(it) } // prints Kotlin and ignores null
}
```
* Интересно, что toString(), вызванный на null-объекте, вернет "null"

Элвис оператор в [официальной документации](https://kotlinlang.org/docs/null-safety.html#elvis-operator)

Оператор позволяет сократить блок if/else при проверке null-переменной:
```kotlin
// До применения Элвис оператора
val l: Int = if (b != null) b.length else -1
// После применения Элвис оператора
val l = b?.length ?: -1
```
* Также для любителей NPE можно его получить, но осознанно:
```kotlin
val l = b!!.length
```
>  По этому блоку вопросов нет, но должен сказать, что это сделано прекрасно и радует глаз

### 5. Core методы из функционального сахара (.map, .let, .apply, .also, .with, .run). Отличие, где используются
[Официальная документация](https://kotlinlang.org/docs/scope-functions.html)

Общая информация:
* apply and also return the context object
* let, run, and with return the lambda result

Далее по каждой функции отдельно:
* .let (и .map), пример разобрал в [Functions.kt](../src/main/kotlin/ru/bryanin/dev/GPB_questions/Functions.kt)
```kotlin
// До применения .let
val numbers = mutableListOf("one", "two", "three", "four", "five")
val resultList = numbers.map { it.length }.filter { it > 3 }
println(resultList)
// После применения .let
val numbers = mutableListOf("one", "two", "three", "four", "five")
numbers.map { it.length }.filter { it > 3 }.let {
    println(it)
    // and more function calls if needed
} 
```
Еще один пример из документации:
```kotlin
// Код до применения .let
val alice = Person("Alice", 20, "Amsterdam")
println(alice)
alice.moveTo("London")
alice.incrementAge()
println(alice)
// Код после применения .let
Person("Alice", 20, "Amsterdam").let {
    println(it)
    it.moveTo("London")
    it.incrementAge()
    println(it)
}
```
Далее в документации сказано: "Scope functions don't introduce any new technical capabilities, but they can make your code more concise and readable"
> Я вообще не вижу удобства и какого-то профита от такого подхода. Можешь привести примеры?

* .apply
Пример:
```kotlin
val adam = Person("Adam").apply {
    age = 32
    city = "London"        
}
println(adam)
```
> Смотрел примеры, и я так понимаю, что функция полезна в тех случаях, когда требуется создание экземпляра, у которого следует инициализировать некоторые свойства. Это как-то визуально смотрится любопытно, но сильного профита я не понял. Что я упустил?
* .also
Пример:
```kotlin
val numbers = mutableListOf("one", "two", "three")
numbers
    .also { println("The list elements before adding new one: $it") }
    .add("four")
```
> Удобно, это как какой-то промежуточный шаг в билдерах, например

* .with
Функция .with позволяет выполнить несколько операций над одним объектом, не повторяя его имени.

Пример: 
```kotlin
val numbers = mutableListOf("one", "two", "three")
with(numbers) {
    println("'with' is called with argument $this")
    println("It contains $size elements")
}
// или
val numbers = mutableListOf("one", "two", "three")
val firstAndLast = with(numbers) {
    "The first element is ${first()}," +
            " the last element is ${last()}"
}
println(firstAndLast)
```
Функция похожа на apply, однако, "она возвращает результат последнего выражения в теле лямбда-выражения. Если вам нужен объект-получатель, то используйте apply"

* .run
В документации сказано: "run is useful when your lambda both initializes objects and computes the return value"
> Я посмотрел примеры. Функция похожа на with, но вызывается на объекте. Похожа на let, но я не вижу обращения к полям объекта через it. В общем, требуется посмотреть РЕАЛЬНЫЕ примеры использования 
```kotlin
val service = MultiportService("https://example.kotlinlang.org", 80)

val result = service.run {
    port = 8080
    query(prepareRequest() + " to port $port")
}

// the same code written with let() function:
val letResult = service.let {
    it.port = 8080
    it.query(it.prepareRequest() + " to port ${it.port}")
}
```

> По всем этим функциям хотелось бы посмотреть реальные примеры из промышленного кода

### 6. Библиотека Result<T>. Что из себя представляет, разница success, failure стейтов. Понимание методов .map. mapCatching, fold, onSuccess, onFailure
#### Библиотека Result<T>
[Официальная документация](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-result/)
[Статья на Baeldung](https://www.baeldung.com/kotlin/result-class)
> Класс Result<T> оборачивает результат выполнения операции, и вернет либо инкапсулированный в себя объект, либо инкапсулированное исключение, при этом есть куча удобных методов для работы с сущностями. Верно понял суть?
Пример использования Result:
```kotlin
fun divide(a: Int, b: Int): Result<Int> = if (b != 0) {
    Result.success(a / b)
} else {
    Result.failure(Exception("Division by zero is not allowed."))
}
```
#### Разница success, failure стейтов
Класс Result<T> обладает свойствами isFailure и isSuccess, которые возвращают Boolean и противоположны по смыслу: isFailure вернет true, если инстанс объекта в Result - ошибка, а isSuccess вернет true, если ошибки нет

#### Понимание методов .map. mapCatching, fold, onSuccess, onFailure

В документации указаны следующие функции:
* exceptionOrNull() возвращает Exception, если он внутри, и null, если isSuccess == true
* getOrNull() возвращает инкапсулированный объект, если isSuccess == true и вернет null, если isFailure == true
* toString вернет Success(v), если isSuccess == true и Failure(x), если isFailure == true

А эти функции отмечены как "Companion Object Functions":
* failure() принимает Exception, возвращает инкапсулированный Exception
* success() принимает объект, возвращает инкапсулированный объект

Следующие функции помечены как "Extension Functions":
* fold() возвращает либо результат onSuccess(), т.е. инкапсулированный объект, если он внутри, либо результат onFailure(), т.е. Exception, если он внутри
* getOrDefault() возвращает либо инкапсулированный объект, если он внутри, либо заданное дефолтное значение, передаваемое в параметр этой функции
* getOrElse() 
> Не очень понял, в чем разница с fold()
* getOrThrow() возвращает либо инкапсулированный объект, если он внутри, либо выбрасывает исключение (не инкапсулированное в него)
* map() обрабатывает результат выдачи success()
* mapCatching() - это map() в блоке try/catch
* onSuccess() - это как peek() над success()
* onFailure() - это как peek() над failure()
* recover() - это как map(), но для работы с failure()
Пример:
```kotlin
fun createOrUpdate(product: Product) = 
	productService.update(product) // Returns Result.failure when product is not persisted
   		.recover { productRepository.create(product) }
```
* recoverCatching() - это как recover(), но внутри try/catch

Также нашел информацию об утилитной функции runCatching(), которая является альтернативой try/catch в Java:
```kotlin
fun divide(a: Int, b: Int): Result {
    return runCatching {
        a / b
    }
} 
```
> Библитека очень понравилась, нужна практика

### 7. @JvmStatic аннотация

Официальная документация: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/

Статья на Baeldung: https://www.baeldung.com/kotlin/jvmstatic-annotation
> В статье говорится, что "The most important use case for the @JvmStatic annotation is Java interoperability" и показан пример. В примерах выше говорится, что функцию companion object после добавления аннотации @JvmStatic можно вызывать не только так: Math.Companion.abs(-2), но и так Math.abs(-2). В нашей работе какие самые популярные кейсы применения @JvmStatic?

___

## <a id="Spring">Spring</a>
### 1. Нюансы инициализаций в связке с Kotlin
> Когда я задал этот вопрос, получил подсказку: @ConstructorBinding, @Autowired с lateinit var, конструкторы с val + @COmponent etc

### 2. AOP (понимание)
### 3. @Transactional (настройка изоляций), ручное открытие и закрытие транзакций через TM
### 4. @ConditionalOnProperty и другие — инициализации бинов при определенных условиях

___

## <a id="MQ">MQ</a>
### 1. JmsTemplate
### 2. Заголовки CorrelationId, ReplyToQ
### 3. Почитать про IBM MQ. QueueManager, channel, авторизация

___

## <a id="ProjectReactor">ProjectReactor</a>
### 1. Что такое Mono, Flux
### 2. Разница .map и .flatMap
### 3. onNext, onComplete, onError
### 4. Что такое сигналы 

___
