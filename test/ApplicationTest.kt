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
<<<<<<< HEAD
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*
=======
import com.msyiszk.domain.service.UserService
import io.ktor.jackson.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.koin.java.KoinJavaComponent.inject
import org.koin.ktor.ext.inject
>>>>>>> features/add_test_config

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("HELLO WORLD!", response.content)
            }
        }
    }
<<<<<<< HEAD
=======

    @Test
    fun testUser(){
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/user/1"){
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
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/user"){
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody("""{"name":"test", "email":"test@example.com"}""")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("""
                    |{
                    |  "status" : "OK",
                    |  "id" : 1
                    |}""".trimMargin(), response.content)
            }
        }
    }
>>>>>>> features/add_test_config
}
