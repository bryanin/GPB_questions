package ru.bryanin.dev.GPB_questions.mvc

import org.springframework.stereotype.Service
import java.util.*

@Service
class KotlinClientService {

    fun getAllClients(): List<Client> {
        return listOf(
            Client("User 1", UUID.randomUUID()),
            Client("User 2", UUID.randomUUID()),
            Client("User 3", UUID.randomUUID()),
            Client("User 4", UUID.randomUUID()),
            Client("User 5", UUID.randomUUID())
        )
    }

}