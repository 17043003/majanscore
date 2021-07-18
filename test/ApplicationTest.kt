package com.msyiszk

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import com.typesafe.config.ConfigFactory
import io.ktor.config.*
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

private val testConfig = createTestEnvironment{
    config = HoconApplicationConfig(ConfigFactory.load("test.conf"))
}

class ApplicationTest {
    @Test
    fun testRoot() {
//        withTestApplication({ module(testing = true) }) {
        withApplication(testConfig) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }

    @Test
    fun testRegisterUser() {
        withApplication(testConfig) {
            handleRequest(HttpMethod.Post, "/user") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"name":"test", "email":"test@example.com", "password":"password"}""")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(
                    """
                    |{
                    |  "status" : "OK",
                    |  "id" : 1
                    |}""".trimMargin(), response.content
                )
            }
        }
    }

    @Test
    fun testGetUser(){
        withApplication(testConfig) {
            handleRequest(HttpMethod.Get, "/user/1"){
                val jwt = transaction {
                    val jwtIssuer = environment.config.property("jwt.domain").getString()
                    val jwtAudience = environment.config.property("jwt.audience").getString()
                    val jwtRealm = environment.config.property("jwt.realm").getString()
                    val algorithm = Algorithm.HMAC256("secret")

                    JWT.create()
                        .withAudience(jwtAudience)
                        .withExpiresAt(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC))) // 1日間
                        .withClaim(1.toString(), id)
                        .withIssuer(jwtIssuer)
                        .sign(algorithm)
                }
                addHeader("Authorization", "bearer $jwt")
                addHeader("Content-Type", "application/json")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("""
                    |{
                    |  "id" : 1,
                    |  "name" : "test",
                    |  "email" : "test@example.com"
                    |}""".trimMargin(), response.content)
            }
        }
    }
}
