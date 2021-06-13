package com.msyiszk

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.features.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import com.msyiszk.domain.service.UserService
import com.msyiszk.domain.service.testModule
import com.msyiszk.domain.service.userModule
import com.msyiszk.infrastructure.DataBaseUtil
import com.msyiszk.presentation.controller.userController
import io.ktor.auth.jwt.*
import io.ktor.request.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.koin.logger.SLF4JLogger
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

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

    val jwtIssuer = environment.config.property("ktor.jwt.domain").getString()
    val jwtAudience = environment.config.property("ktor.jwt.audience").getString()
    val jwtRealm = environment.config.property("ktor.jwt.realm").getString()

    install(Authentication) {
        jwt{
            realm = jwtRealm
            verifier(makeJwtVerifier(jwtIssuer, jwtAudience))
            validate { jwtCredential ->
                if(jwtCredential.payload.audience.contains(jwtAudience)) JWTPrincipal(jwtCredential.payload) else null
            }
        }
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
        post("/auth"){
            DataBaseUtil.connectDatabase()
            call.receive<UserAuthRequest>().let {
                call.respond(mapOf("jwt" to transaction {
                    val user = userService.getUserByEmail(it.email)

                    if(it.password != user.password){
                            return@transaction ""
                    }
                    JWT.create()
                        .withAudience(jwtAudience)
                        .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))) // 1日間
                        .withClaim(user.id.toString(), id)
                        .withIssuer(jwtIssuer)
                        .sign(algorithm)
                }))
            }
        }

        get("/") {
            call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
        }
        userController(userService)
    }
}

private val algorithm = Algorithm.HMAC256("secret")
private fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT.require(algorithm).withAudience(audience).withIssuer(issuer).build()
data class UserAuthRequest(val email: String, val password: String)
