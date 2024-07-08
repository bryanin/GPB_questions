package ru.bryanin.dev.GPB_questions.web.nonblocking.service

import org.springframework.cloud.openfeign.support.SpringMvcContract
import org.springframework.stereotype.Service
import reactivefeign.ReactiveContract
import reactivefeign.webclient.WebReactiveFeign
import reactor.core.publisher.Flux
import ru.bryanin.dev.GPB_questions.web.commonData.Comment
import ru.bryanin.dev.GPB_questions.web.nonblocking.webClient.ReactiveWebClient

@Service
class ReactiveCommentService() {

    lateinit var webClient: ReactiveWebClient

    val client: ReactiveWebClient = WebReactiveFeign.builder<ReactiveWebClient>()
        .contract(ReactiveContract(SpringMvcContract()))
        .target(ReactiveWebClient::class.java, "https://jsonplaceholder.typicode.com")


    val response = client.getComments()

    fun getAllComments(): Flux<Comment> {
        return response.doOnNext { println(it) }.cast(Comment::class.java);
    }


}