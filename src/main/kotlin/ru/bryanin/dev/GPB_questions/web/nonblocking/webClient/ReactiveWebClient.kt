package ru.bryanin.dev.GPB_questions.web.nonblocking.webClient

import feign.RequestLine
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import reactivefeign.spring.config.ReactiveFeignClient
import reactor.core.publisher.Flux
import ru.bryanin.dev.GPB_questions.web.commonData.Comment


@Component
@ReactiveFeignClient(
    url = "https://jsonplaceholder.typicode.com",
    name = "commentsFetchClient"
)
interface ReactiveWebClient {

    @RequestLine("GET /comments")
    fun getComments(): Flux<Comment>



}