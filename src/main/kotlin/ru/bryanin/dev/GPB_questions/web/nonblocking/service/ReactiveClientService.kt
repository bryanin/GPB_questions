package ru.bryanin.dev.GPB_questions.web.nonblocking.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.bryanin.dev.GPB_questions.web.commonData.Client
import java.util.*

@Service
class ReactiveClientService() {

    fun getAllClients(): Flux<Client> {
        return Flux.fromIterable(
            listOf(
                Client("User 1", UUID.randomUUID()),
                Client("User 2", UUID.randomUUID()),
                Client("User 3", UUID.randomUUID()),
                Client("User 4", UUID.randomUUID()),
                Client("User 5", UUID.randomUUID())
            )
        )
    }

    fun getClient(id: UUID): Mono<Client> {
        return Mono.just(Client("User 6", UUID.randomUUID())
        )
    }
}