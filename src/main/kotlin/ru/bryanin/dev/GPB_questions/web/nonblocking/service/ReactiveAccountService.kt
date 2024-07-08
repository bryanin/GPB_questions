package ru.bryanin.dev.GPB_questions.web.nonblocking.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.bryanin.dev.GPB_questions.web.commonData.Account
import java.util.*

@Service
class ReactiveAccountService {

    fun getAllAccounts(): Flux<Account> {
        return Flux.fromIterable(
            listOf(
                Account(UUID.randomUUID(), "Счет 1", UUID.randomUUID()),
                Account(UUID.randomUUID(), "Счет 2", UUID.randomUUID()),
                Account(UUID.randomUUID(), "Счет 3", UUID.randomUUID()),
                Account(UUID.randomUUID(), "Счет 4", UUID.randomUUID()),
                Account(UUID.randomUUID(), "Счет 5", UUID.randomUUID()),
            )
        )
    }

    fun getAccount(id: UUID): Mono<Account> {
        return Mono.just(
            Account(UUID.randomUUID(), "Счет 6", UUID.randomUUID())
        )
    }
}