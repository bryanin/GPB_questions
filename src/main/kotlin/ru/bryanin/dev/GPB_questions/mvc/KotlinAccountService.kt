package ru.bryanin.dev.GPB_questions.mvc

import org.springframework.stereotype.Service
import java.util.*

@Service
class KotlinAccountService {

    fun getAllAccounts(): List<Account> {
        return listOf(
            Account(UUID.randomUUID(), "Счет 1", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 2", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 3", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 4", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 5", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 6", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 7", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 8", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 9", UUID.randomUUID()),
            Account(UUID.randomUUID(), "Счет 10", UUID.randomUUID()),
        )
    }
}