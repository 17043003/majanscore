package com.msyiszk.presentation.controller

import com.msyiszk.domain.service.QuizService
import com.msyiszk.domain.service.UserService
import com.msyiszk.presentation.form.RegisterQuizRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

data class RegisterQuizLocation(val title: String, val content: String, val user_id: Int)

fun Routing.quizController(quizService: QuizService) {
    authenticate {
        post("/quiz") {
            val registerQuizParams = call.receive<RegisterQuizLocation>()
            val quizRequest =
                RegisterQuizRequest(registerQuizParams.title, registerQuizParams.content, registerQuizParams.user_id)
            val id = quizService.registerQuiz(quizRequest)
            call.respond(mapOf("status" to id))
        }
    }
}