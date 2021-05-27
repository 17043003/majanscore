package com.msyiszk.infrastructure

import org.jetbrains.exposed.sql.Database

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