package ru.bryanin.dev.GPB_questions.web.blocking

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.bryanin.dev.GPB_questions.web.commonData.Account
import ru.bryanin.dev.GPB_questions.web.commonData.Client

@RestController
@RequestMapping("/api/blocking")
// Внедрение зависимостей через конструктор
class WebController(val webAccountService: WebAccountService) {

    @Autowired
    // Внедрение зависимостей через поле класса и @Autowired
    lateinit var webClientService: WebClientService

    @GetMapping("/client")
    fun getAllClients(): List<Client> {
        return webClientService.getAllClients()
    }

    @GetMapping("/account")
    fun getAllAccounts(): List<Account> {
        return webAccountService.getAllAccounts()
    }
}