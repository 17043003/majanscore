package com.msyiszk.infrastructure

import com.msyiszk.domain.model.User
import com.msyiszk.domain.repository.UserRepository
import com.msyiszk.presentation.form.RegisterUserRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl: UserRepository {
    override fun register(name: String, email: String): Int {
        DataBaseUtil.connectDatabase()

        return transaction {
            addLogger(StdOutSqlLogger)

            // return user id when user is already registered
            val userRow = UserTable.select{ UserTable.email like email }.singleOrNull()
            if(userRow != null){
                return@transaction userRow[UserTable.id]
            }

            UserTable.insert {
                it[this.name] = name
                it[this.email] = email
            } get UserTable.id
        }
    }

    override fun findUser(id: Int): User {
        DataBaseUtil.connectDatabase()

        return transaction {
            val userRow = UserTable.select { UserTable.id eq id }.single()
            User(userRow[UserTable.id], userRow[UserTable.name], userRow[UserTable.email])
        }
    }

    override fun findUser(email: String): User {
        DataBaseUtil.connectDatabase()

        return transaction {
            val userRow = UserTable.select{ UserTable.email eq email }.single()
            User(userRow[UserTable.id], userRow[UserTable.name], userRow[UserTable.email])
        }
    }

    object UserTable: Table("user"){
        val id = integer("id").autoIncrement()
        val name = varchar("name", 32)
        val email = varchar("email", 128).uniqueIndex()
        override val primaryKey = PrimaryKey(id)
    }
}