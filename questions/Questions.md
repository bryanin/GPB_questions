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

#### Типы переменных
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

#### lateinit, by lazy

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
> Что касается практического смысла применения object или companion object - здесь, как я понимаю, аналогия с static в Java. Верно?

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

#### Null safely парадигма
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
* Интересно, что toString(), вызванный на null-объекте, вернет строку "null"

#### Элвис оператор 
[Официальная документация](https://kotlinlang.org/docs/null-safety.html#elvis-operator)

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
> Сначала не очень понял, в чем разница с Streams в Java, но потом перечитал и подумал, что все-такие не все эти методы возвращают лямбду и есть просто чуть больше удобства в работе с объектами

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
> Когда я задал этот вопрос, получил подсказку: 
> @ConstructorBinding, 
> @Autowired с lateinit var, 
> конструкторы с val + @Component 
> etc
#### @ConstructorBinding
[Статья на Baeldung](https://www.baeldung.com/kotlin/spring-boot-configurationproperties)
Приводится пример:
```yaml
# application.yaml 
api:
    clientId: "client123"
    url: "https://api.url.com"
    key: "api-access-key"
```
```kotlin
// class ApiConfiguration
@ConfigurationProperties(prefix = "api")
@ConstructorBinding
data class ApiConfiguration(
    val clientId: String,
    val url: String,
    val key: String
)
// class AppConfiguration
@Configuration
@EnableConfigurationProperties(ApiConfiguration::class)
class AppConfiguration {
    @Bean
    fun apiConfiguration(): ApiConfiguration {
        return ApiConfiguration()
    }
}


```
В статье говорится, что без этой аннотации мы могли бы применять только var при создании полей класса ApiConfiguration
> В этом суть вопроса?

#### @Autowired с lateinit var
Посмотрел примеры приложений Spring Boot с Kotlin. Встречаю такую конструкцию при объявлении переменных под DI:
```kotlin
@Autowired
lateinit var kotlinClientService: KotlinClientService
```
И через конструктор:
```kotlin
@RestController
class KotlinController(val kotlinAccountService: KotlinAccountService)
```
> Какой из этих способов корректный и общепринятый? При разработке на Java при добавлении @Autowired над полем сама Idea предлагает заменить это на DI через конструктор. А здесь как?
> (конструкторы с val + @Component) - ты имел в виду как раз второй вариант с внедрением через конструктор, я правильно понял?

### 2. AOP (понимание)
[Официальная документация](https://docs.spring.io/spring-framework/reference/core/aop.html)
> На учебном проекте применял Spring AOP для логирования - общее понимание есть, но опыта маловато...

[Ссылка на репозиорий с примером](https://github.com/costinm92/kotlin-logging-with-spring-aop) (нашел в открытом доступе)

> Не понял только, где @Pointcut объявлен

### 3. @Transactional (настройка изоляций), ручное открытие и закрытие транзакций через TM

#### Транзакции

Немного теории:
* Часто встречающиеся аномалии при параллельной работе с данными:
  * Dirty read (грязное чтение)
  * Lost update (потерянное обновление)
  * Non-repeatable read (неповторяющееся чтение)
  * Phantom read (фантомное чтение)
* Существуют 4 уровня изолированности транзакций:
  * Read uncommitted (чтение незафиксированных данных)
  * Read committed (чтение зафиксированных данных)
  * Repeatable read (неизменные данные при повторяющихся чтениях)
  * Serializable (последовательность выполнения транзакций)

Соотношение уровней изоляции и встречающихся аномалий в PostgreSQL

![Изоляции и присущие им аномалии](../src/main/resources/images/isolation_levels_and_anomalies.png)

####  @Transactional в Spring Framework

Статья на [официальном сайте Spring Framework](https://spring.io/blog/2019/05/16/reactive-transactions-with-spring)

Статья на [Гибхабе про @Transactional в НЕреактивном стеке](https://habr.com/ru/articles/532000/)

* @Transactional - это аннотация из пакета org.springframework.transaction.annotation
* TM - это Transaction Manager

#### Управление транзакциями

По умолчанию в PostgreSQL применяется уровень изоляции "Read committed". Как происходит управление:
##### Управление транзакциями в PostgreSQL
Используется команда SET TRANSACTION:
```postgresql
SET TRANSACTION REPEATABLE READ;
```
##### Управление транзакциями в Spring Framework
```java
public class IsolationLevelExample {
  private static final int ISOLATION_LEVEL = Connection.TRANSACTION_REPEATABLE_READ;
  public static void main(String[] args) throws SQLException, InterruptedException {
    try (
            final Connection connection = Repository.getConnectionH2();
            final Statement statement = connection.createStatement()
    ) {
      connection.setAutoCommit(false);
      connection.setTransactionIsolation(ISOLATION_LEVEL);
      statement.executeUpdate("UPDATE person SET balance = 100000 WHERE id = 1");
      new OtherTransaction().start();
      Thread.sleep(2000);
      connection.rollback();
    }
  }
}
```

##### Создание транзакций в JDBC
```java
import java.sql.Connection;
class UserConnection {
  Connection connection = dataSource.getConnection(); // (1)
    try (connection) {
        connection.setAutoCommit(false); // (2)
        // execute some SQL statements...
        connection.commit(); // (3)
    } catch (SQLException e) {
        connection.rollback(); // (4)
    }    
}
```
##### Программное управление транзакциями Spring
```java
@Service
public class UserService {
    @Autowired
    private TransactionTemplate template;
    public Long registerUser(User user) {
        Long id = template.execute(status ->  {
            // execute some SQL that e.g.
            // inserts the user into the db and returns the autogenerated id
            return id;
        });
    }
}
```
Из [этой](https://struchkov.dev/blog/ru/transaction-jdbc-and-spring-boot/) и [этой](https://struchkov.dev/blog/ru/transactional-isolation-levels/) статей:

По сравнению с обычным примером JDBC:
1. Вам не нужно открывать и закрывать соединений с базой данных самостоятельно. Вместо этого, вы используете Transaction Callbacks.
2. Также не нужно ловить SQLExceptions, так как Spring преобразует их в исключения времени выполнения. 
3. Кроме того, вы лучше интегрируетесь в экосистему Spring. TransactionTemplate будет использовать TransactionManager внутри, который будет использовать источник данных. Всё это бины, которые вы укажете в конфигурации, но о которых вам больше не придется беспокоиться в дальнейшем.

Хотя это, и выглядит небольшим улучшением по сравнению с JDBC, программное управление транзакциями — это не самый лучший способ. Вместо этого, используйте декларативное управление транзакциями.

##### Декларативное управление транзанкциями

Нужно сделать две вещи:
1. Убедиться, что одна из Spring конфигурации аннотирована @EnableTransactionManagement. В SpringBoot даже этого делать не нужно. 
2. Убедиться, что вы указали менеджер транзакций в конфигурации. Это делается в любом случае.

И тогда Spring будет обрабатывать транзакции за вас. Любой публичный метод бина, который вы аннотируете @Transactional, будет выполняться внутри транзакции базы данных. Чтобы аннотация @Transactional заработала, делаем следующее:
```java
@Configuration
@EnableTransactionManagement
public class MySpringConfig {
    @Bean
    public PlatformTransactionManager txManager() {
        return yourTxManager; // more on that later
    }
}
```

```java
@Transactional
public class UserService {
  public Long registerUser(User user) {
    Connection connection = dataSource.getConnection(); // (1)
    try (connection) {
      connection.setAutoCommit(false); // (1)
      // execute some SQL that e.g.
      // inserts the user into the db and retrieves the autogenerated id
      // userDao.save(user); <(2)
      connection.commit(); // (1)
    } catch (SQLException e) {
      connection.rollback(); // (1)
    }
  }
}
```

##### Участие TransactionManager в управлении транзакциями
Конфигурация:
```java
class Config() {
  @Bean
  public DataSource dataSource() {
    return new MysqlDataSource(); // (1)
  }
  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(dataSource()); // (2)
  }
}
```
В итоге:
1. Если Spring обнаруживает аннотацию @Transactional на бине, он создаёт динамический прокси этого бина 
2. Прокси имеет доступ к менеджеру транзакций и будет просить его открывать и закрывать транзакции/соединения
3. Сам менеджер транзакций будет просто управлять соединением JDBC

> Какой подход лучше использовать? 


### 4. @ConditionalOnProperty и другие — инициализации бинов при определенных условиях
[Статья на Baeldung](https://www.baeldung.com/spring-conditionalonproperty)
Есть пример на Java:
```java
// NotificationSender
public interface NotificationSender {
    String send(String message);
}
```
```yaml
# application.properties
notification:
  service: email
```
```java
// EmailNotification
public class EmailNotification implements NotificationSender {
    @Override
    public String send(String message) {
        return "Email Notification: " + message;
    }
}
```
Здесь мы добавляем вторую реализацию интерфейса:
```java
// SmsNotification
public class SmsNotification implements NotificationSender {
    @Override
    public String send(String message) {
        return "SMS Notification: " + message;
    }
}
```
Цитирую статью: "Since we have two implementations, let’s see how we can use @ConditionalOnProperty to load the right NotificationSender bean conditionally"
```java
// Класс, в котором требуется бины двух реализаций NotificationSender
class AnotherOne {
    @Bean(name = "emailNotification")
    @ConditionalOnProperty(prefix = "notification", name = "service")
    public NotificationSender notificationSender() {
        return new EmailNotification();
    }
    @Bean(name = "smsNotification")
    @ConditionalOnProperty(prefix = "notification", name = "service", havingValue = "sms")
    public NotificationSender notificationSender2() {
        return new SmsNotification();
    }
}
```
Комментарий автора: "With the help of the havingValue attribute, we made it clear that we want to load SmsNotification only when notification.service is set to sms"

Тесты:
```java
class Testing() {
    @Test
    public void whenValueSetToEmail_thenCreateEmailNotification() {
        this.contextRunner.withPropertyValues("notification.service=email")
                .withUserConfiguration(NotificationConfig.class)
                .run(context -> {
                    assertThat(context).hasBean("emailNotification");
                    NotificationSender notificationSender = context.getBean(EmailNotification.class);
                    assertThat(notificationSender.send("Hello From Baeldung!")).isEqualTo("Email Notification: Hello From Baeldung!");
                    assertThat(context).doesNotHaveBean("smsNotification");
                });
    }
}
```
> В итоге, добавляя havingValue = "sms" в параметры аннотации @ConditionalOnProperty, мы сказали, что если этого атрибута нет в "notification.service", то этот бин не попадает в Контекст? Верно?
___

## <a id="MQ">MQ</a>
### 1. JmsTemplate
[Официальная документация](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jms/core/JmsTemplate.html)

[Статья на Баелдунге](https://www.baeldung.com/spring-jms)


JMS - это Java Message Service. Ключевой класс - JdbcTemplate. 

Пример работы с JMS из [официального гида](https://spring.io/guides/gs/messaging-jms): 
1. Добавляем зависимости JMS в проект
2. Имеется класс Получатель (message-driven POJO), содержащий метод отправки сообщений с аннотацией @JmsListener(destination = "???", containerFactory = "???")
3. Над классом, содержащим метод main() или над другим конфигурационным классом вешаем аннотацию @EnableJms
4. Создаем бин фабрики, возвращающего JmsListenerContainerFactory<?>
5. Создаем бин конвертера, возвращающего MessageConverter
6. Создаем бин JmsTemplate
7. Вызываем к него метод convertAndSend(), передавая параметрами название destination и передаваемый POJO

### 2. Заголовки CorrelationId, ReplyToQ

[Вопрос и ответ на StackOverFlow](https://stackoverflow.com/questions/56473137/jms-correlationid-vs-replyto)

> Насколько понял, CorrelationId передается в сообщении, чтобы связать запрос и ответ. Что такое ReplyToQ, наверное, нужно смотреть в прикладном контексте (в зависимости от реализации MQ)?

### 3. Почитать про IBM MQ. QueueManager, channel, авторизация

#### IBM MQ QueueManager
[Официальная документация](https://www.ibm.com/docs/fr/ibm-mq/9.2?topic=java-mqqueuemanager)

[A Simple Guide to Using IBM MQ with Java Messaging Service](https://levioconsulting.com/insights/a-simple-guide-to-using-ibm-mq-with-java-messaging-service/)


#### IBM MQ channel
[Официальная документация](https://www.ibm.com/docs/en/ibm-mq/9.2?topic=explorer-channels)

Насколько понял, для решения разных задач в IBM MQ можно использовать 3 различных типа канала: 
1. Message channel (однонаправленный для связи двух менеджеров очередей)
2. MQI channel (двунаправленный для связи приложения и менеджера очереди)
3. AMQP channel (двунаправленный для связи приложения и менеджера очереди)

> Не особо уловил разницу между 2 и 3. Нужна практика

#### IBM MQ авторизация
[Официальная документация](https://www.ibm.com/docs/en/ibm-mq/9.2?topic=mechanisms-authorization-in-mq)

___

## <a id="ProjectReactor">ProjectReactor</a>
[Официальная документация](https://projectreactor.io/)

[Цикл статей на Хабре о реактивном программировании Spring boot + Java + WebFlux](https://habr.com/ru/articles/565000/)

[Вот еще интересная статья про переход с блокирующего сервиса с Java на неблокирующий с Corutines](https://habr.com/ru/articles/537716/)

> У меня возникли некоторые сложности при реализации сервиса с технологиями WebFlux + ReactiveFeignWebClient. Прошу посмотреть код [здесь](../src/main/kotlin/ru/bryanin/dev/GPB_questions/web/nonblocking/webClient/ReactiveWebClient.kt)

### 1. Что такое Mono, Flux
Mono и Flux - реализации интерфейса Publisher<T> из пакета org.reactivestreams. При этом Mono - сущность, содержащая ноль или один экземпляр оборачиваемого класса, Flux - коллекция таких экземпляров
### 2. Разница .map и .flatMap
[Статья на Baeldung](https://www.baeldung.com/java-reactor-map-flatmap)

#### .map()
Метод map() используется для преобразования каждого элемента потока с помощью заданной функции:
```java
@Component
class MapExample() {
  Function<String, String> mapper = String::toUpperCase;
  Flux<String> inFlux = Flux.just("baeldung", ".", "com");
  Flux<String> outFlux = inFlux.map(mapper);
}
//
@Test
class TestingMap() {
    void testMap() {
      StepVerifier.create(outFlux)
              .expectNext("BAELDUNG", ".", "COM")
              .expectComplete()
              .verify();
    }
}
```
#### .flatMap()
Метод .flatMap() может преобразовывать один элемент в несколько, превращая поток потоков в один объединенный поток
```java
@Component
class FlatMapExample() {
  Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase().split(""));
  Flux<String> inFlux = Flux.just("baeldung", ".", "com");
  Flux<String> outFlux = inFlux.flatMap(mapper);
}
//
@Test
class TestingFlatMap() {
    void testFlatMap() {
      List<String> output = new ArrayList<>();
      outFlux.subscribe(output::add);
      assertThat(output).containsExactlyInAnyOrder("B", "A", "E", "L", "D", "U", "N", "G", ".", "C", "O", "M");
    }
}
```

### 3. onNext, onComplete, onError

Reactive Stream основан на модели Издатель-Подписчик и оперирует 4 интерфейсами:
* Publisher - поставщик данных в виде событий
* Subscriber - получает/обрабатывает события от Издателя
* Subscription - определяет однозначную связь между Подписчиком и Издателем. Имеет 2 метода: запросить (request) и отменить (cancel) подписку
* Processor - представляет собой стадию обработки

У Подписчика есть 4 метода для работы с полученными событиями:
* onSubscribe(s: Subscription) - метод вызывается автоматически, когда Издатель регистрируется после того, как Подписчик вызывает метод subscribe()
* onNext(t: T) - метод вызывается при каждой передаче данных от Издателя к Подписчику
* onError(t: Throwable) - метод вызывается, если при обработке события возникает какая-либо ошибка
* onComplete() - метод вызывается, если при обработке события не было ошибки и все события успешно завершены

![onNext, onComplete](../src/main/resources/images/reactive.jpeg)

> В теории понятно, на практике нужно пробовать

### 4. Что такое сигналы 

> Не понял, что имеется в виду
___
