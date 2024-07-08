package ru.bryanin.dev.GPB_questions.web.blocking

import org.springframework.stereotype.Service
import ru.bryanin.dev.GPB_questions.web.commonData.Account
import java.util.*

@Service
class WebAccountService {

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