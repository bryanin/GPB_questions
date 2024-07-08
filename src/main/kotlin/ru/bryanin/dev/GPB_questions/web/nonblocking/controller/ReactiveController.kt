package ru.bryanin.dev.GPB_questions.web.nonblocking.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.bryanin.dev.GPB_questions.web.commonData.Account
import ru.bryanin.dev.GPB_questions.web.commonData.Client
import ru.bryanin.dev.GPB_questions.web.commonData.Comment
import ru.bryanin.dev.GPB_questions.web.nonblocking.service.ReactiveAccountService
import ru.bryanin.dev.GPB_questions.web.nonblocking.service.ReactiveClientService
import ru.bryanin.dev.GPB_questions.web.nonblocking.service.ReactiveCommentService
import java.util.UUID

@RestController
@RequestMapping("/api/nonblocking")
// Внедрение зависимостей через конструктор
class ReactiveController (val reactiveAccountService: ReactiveAccountService) {

    @Autowired
    // Внедрение зависимостей через поле класса и @Autowired
    lateinit var reactiveClientService: ReactiveClientService
    lateinit var reactiveCommentService: ReactiveCommentService

    @GetMapping("/client")
    fun getAllClients(): Flux<Client> {
        return reactiveClientService.getAllClients()
    }

    @GetMapping("/client/{id}")
    fun getClient(@PathVariable("id") id: UUID): Mono<Client> {
        return reactiveClientService.getClient(id)
    }

    @GetMapping("/account")
    fun getAllAccounts(): Flux<Account> {
        return reactiveAccountService.getAllAccounts()
    }

    @GetMapping("/account/{id}")
    fun getAccount(@PathVariable("id") id: UUID): Mono<Account> {
        return reactiveAccountService.getAccount(id)
    }

    @GetMapping("/comment")
    fun getAllComments(): Flux<Comment> {
        return reactiveCommentService.getAllComments()
    }

}