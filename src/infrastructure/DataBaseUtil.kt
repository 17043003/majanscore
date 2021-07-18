package com.msyiszk.infrastructure

import com.msyiszk.domain.model.User
import io.ktor.application.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseUtil {
    private lateinit var url: String
    private lateinit var driver: String
    private lateinit var user: String
    private lateinit var password: String

    fun connectDatabase() {
        Database.connect(
            url = this.url,
            driver = this.driver,
            user = this.user,
            password = this.password
        )
    }

    fun initDatabase(url: String, driver: String, user: String, password: String){
        this.url = url
        this.driver = driver
        this.user = user
        this.password = password

        connectDatabase()
        transaction {
            SchemaUtils.create (UserTable, QuizTable)
        }
    }
}

object UserTable: Table("user"){
    val id = integer("id").autoIncrement()
    val name = varchar("name", 32)
    val email = varchar("email", 128).uniqueIndex()
    val password = varchar("password", 128)
    override val primaryKey = PrimaryKey(id)
}

object QuizTable: Table("quiz"){
    val id = integer("id").autoIncrement()
    val user_id = (integer("user_id") references UserTable.id)
    val title = varchar("title", 128)
    val content = varchar("content", 128)
    override val primaryKey = PrimaryKey(id)
}