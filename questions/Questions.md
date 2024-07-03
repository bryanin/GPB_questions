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

Официальная документация: https://kotlinlang.org/docs/basic-syntax.html#variables
   
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
> У меня так не получилось, ругается компилятор, см. файл [KotlinVariables.kt](../src/main/kotlin/ru/bryanin/dev/questions/KotlinVariables.kt) - в чем ошибка?

Что касается lateinit, by lazy (своими словами):
* lateinit применяется с var в случаях, когда нужна инициализация var после объявления переменной. 
Обычно производится через стороннюю функцию или Dependency Injection
* by lazy применяется с val в случаях, когда нужна инициализация val после объявления переменной
> Хотелось бы подробнее узнать, в каких случаях такой подход применяют, когда это хорошо, когда плохо и т.д.

### 2. Разница в классах. Data class, sealed class, object, companion object

Официальная документация: https://kotlinlang.org/docs/basic-syntax.html#creating-classes-and-instances
   
Дополнительные материалы:
* Data class: https://habr.com/ru/articles/752450/#5
* Sealed class: https://habr.com/ru/articles/752450/#6
* object и companion object: https://bimlibik.github.io/posts/kotlin-object-keyword/

Своими словами: в Java record class сделан по образу Data class, как я понял. Есть и отличия...

Особенности Data class:
* Data class должен принимать как минимум один параметр в конструктор. В теле класса можно добавить переменные
> "В теле класса можно добавить переменные" - какой смысл? 
* От Data class нельзя наследоваться, так как он является final
> Я почитал на тему open классов и понял, что по умолчанию все классы в Kotlin являются final, и если нужно разрешить наследование от них, добавляют модификатор open
* Data class может быть унаследован от других open-классов
* Все параметры основного конструктора должны быть отмечены val или var
> Не понял, почему при добавлении переменных val компилятор их подчеркивает и говорит "public final var param2: Int".
> При этом так называть переменные в других классах я не могу... Компилятор подчеркивает, но не ругается
* Data class не может быть abstract, open, sealed или inner
* Data-классы в Kotlin автоматически генерируют следующие стандартные методы (equals(), hashCode(), toString(), copy(), componentN())

Своими словами:
Sealed class - это класс, ограничивающий набор подклассов, которые могут быть унаследованы от него
> Я вижу большую разницу в синтаксисе по сравнению с реализацией в Java: прямо в теле классе задаются наследуемые классы. 
> Было бы интересно узнать, в каких случаях такой подход используется

### 3. Порядок при инициализации классов
Официальная документация: https://kotlinlang.org/docs/classes.html   
База своими словами:
* Все классы в Kotlin по умолчанию final и наследуются от класса Any
* Если нужен класс, от которого нужно наследоваться, добавляется модификатор доступа open
* Если у дочернего класса есть основной конструктор, то в основной конструктор дочернего класса передаются параметры родительского класса. Если же у дочернего класса нет основного конструктора, каждый объявленный вторичный конструктор должен через super передавать параметры в родительский класс
* При переопределении функций в дочернем классе добавляется override (как ключевое слово, а не как аннотация в Java). Чтобы запретить переопределение, указать ключевое слово final
Что касается порядка инициализации: https://kotlinlang.org/docs/inheritance.html#derived-class-initialization-order
1. Вызывается основной конструктор дочернего класса
2. Вызывается основной конструктор родительского класса
3. Инициализируются статические блоки родительского класса
4. Инициализируются поля и методы родительского класса
5. Инициализируются статические блоки дочернего класса
6. Инициализируются поля и методы дочернего класса
> Верно?

### 4. Null safely парадигма котлина. Элвис оператор «?:»



### 5. Core методы из функционального сахара (.map, .let, .apply, .also, .with, .run). Отличие, где используются
### 6. Библиотека Result<T>. Что из себя представляет, разница success, failure стейтов. Понимание методов .map. mapCatching, fold, onSuccess, onFailure
### 7. @JvmStatic аннотация

Официальная документация: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-static/

Статья на Baeldung: https://www.baeldung.com/kotlin/jvmstatic-annotation
> В статье говорится, что "The most important use case for the @JvmStatic annotation is Java interoperability" и показан пример. В примерах выше говорится, что функцию companion object после добавления аннотации @JvmStatic можно вызывать не только так: Math.Companion.abs(-2), но и так Math.abs(-2). В нашей работе какие самые популярные кейсы применения @JvmStatic?

___

## <a id="Spring">Spring</a>
### 1. Нюансы инициализаций в связке с Kotlin
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
