package com.msyiszk.presentation.controller

import com.msyiszk.AuthUser
import com.msyiszk.domain.service.UserService
import com.msyiszk.presentation.form.RegisterUserRequest
import com.msyiszk.presentation.form.UserInfo
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.NoSuchElementException

data class UserRegisterLocation(val name: String, val email: String, val password: String)

fun Routing.userController(userService: UserService) {
    authenticate {
        get("/user"){
            try {
                val user = userService.getCurrentUser(call.authentication.principal<AuthUser>()!!.id)
                call.respond(UserInfo(user))
            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode(404, "No user is found"))
            }
        }
    }
    post("/user") {
        val registerRequest = call.receive<UserRegisterLocation>()
        val request = RegisterUserRequest(registerRequest.name, registerRequest.email, registerRequest.password)
        val registeredUserId = userService.registerUser(request)
        call.respond(mapOf("status" to "OK", "id" to registeredUserId))
    }
}