package ru.bryanin.dev.GPB_questions.mvc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinController(val kotlinAccountService: KotlinAccountService) {

    @Autowired
    lateinit var kotlinClientService: KotlinClientService

    @GetMapping("/client")
    fun getAllClients(): List<Client> {
        return kotlinClientService.getAllClients()
    }

    @GetMapping("/account")
    fun getAllAccounts(): List<Account> {
        return kotlinAccountService.getAllAccounts()
    }
}