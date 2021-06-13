package com.msyiszk.infrastructure

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