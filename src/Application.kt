package com.msyiszk

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import com.msyiszk.domain.service.UserService
import com.msyiszk.domain.service.testModule
import com.msyiszk.domain.service.userModule
import com.msyiszk.presentation.controller.userController
import com.msyiszk.presentation.form.RegisterUserRequest
import com.msyiszk.presentation.form.UserInfo
import io.ktor.jackson.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(Locations) {
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header("MyCustomHeader")
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Authentication) {
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(DefaultHeaders)
    install(CallLogging)

    install(Koin) {
        SLF4JLogger()
        if(!testing) {
            modules(userModule)
        }
        else{
            modules(testModule)
        }
    }
    val userService: UserService by inject()

    routing {
        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }

        userController(userService)
    }
}

