package com.msyiszk.presentation.controller

import com.msyiszk.AuthUser
import com.msyiszk.domain.service.QuizService
import com.msyiszk.domain.service.UserService
import com.msyiszk.presentation.form.RegisterQuizRequest
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

data class RegisterQuizLocation(
    val title: String,
    val content: String,
    var user_id: Int,
    val drawn: String,
    val round: Int,
    val wind: Int,
    val around: Int,
    val dora: String,
    val point: Int,
    val answer: String,
    val description: String
    )

@Location("/quiz/{quizId}")
data class QuizId(val quizId: Int)

fun Routing.quizController(quizService: QuizService) {
    authenticate {
        get("/quiz"){
            val allQuiz = quizService.getAllQuiz()
            call.respond(HttpStatusCode.OK, allQuiz)
        }
        post("/quiz") {
            val registerQuizParams = call.receive<RegisterQuizLocation>()
            registerQuizParams.user_id = call.authentication.principal<AuthUser>()!!.id
            val quizRequest = toQuizRequest(registerQuizParams)
            val id = quizService.registerQuiz(quizRequest)
            call.respond(mapOf("status" to 200, "id" to id))
        }
        get<QuizId>{
            val quizDetailInfo = quizService.getDetailQuiz(it.quizId)
            call.respond(mapOf("status" to 200, "data" to quizDetailInfo))
        }
    }
}

fun toQuizRequest(quiz: RegisterQuizLocation): RegisterQuizRequest{
    return RegisterQuizRequest(
        quiz.title,
        quiz.content,
        quiz.user_id,
        quiz.drawn,
        quiz.round,
        quiz.wind,
        quiz.around,
        quiz.dora,
        quiz.point,
        quiz.answer,
        quiz.description
    )
}