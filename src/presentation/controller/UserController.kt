package com.msyiszk.presentation.controller

import com.msyiszk.domain.service.UserService
import com.msyiszk.presentation.form.RegisterUserRequest
import com.msyiszk.presentation.form.UserInfo
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

data class UserRegisterLocation(val name: String, val email: String)

fun Routing.userController(userService: UserService) {
    @Location("/user/{id}")
    data class UserLocation(val id: Int)
    get<UserLocation> { request ->
        val id = request.id
        val user = userService.getCurrentUser(id)
        call.respond(UserInfo(user))
    }

    post("/user"){
        val registerRequest = call.receive<UserRegisterLocation>()
        val request = RegisterUserRequest(registerRequest.name, registerRequest.email)
        val registeredUserId = userService.registerUser(request)
        call.respond(mapOf("status" to "OK", "id" to registeredUserId))
    }
}