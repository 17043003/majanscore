package com.msyiszk.infrastructure

import com.msyiszk.domain.model.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table

object DataBaseUtil {
    fun connectDatabase() {
        Database.connect(
            "jdbc:mysql://127.0.0.1:3306/majan_score",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = "mysql"
        )
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