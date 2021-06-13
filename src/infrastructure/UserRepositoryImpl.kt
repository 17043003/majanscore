package com.msyiszk.infrastructure

import com.msyiszk.domain.model.User
import com.msyiszk.domain.repository.UserRepository
import com.msyiszk.presentation.form.RegisterUserRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl: UserRepository {
    override fun register(user: RegisterUserRequest): Int {
        DataBaseUtil.connectDatabase()

        return transaction {
            addLogger(StdOutSqlLogger)

            // return user id when user is already registered
            val userRow = UserTable.select{ UserTable.email like user.email }.singleOrNull()
            if(userRow != null){
                return@transaction userRow[UserTable.id]
            }
            user.password

            UserTable.insert {
                it[this.name] = user.name
                it[this.email] = user.email
                it[this.password] = user.password
            } get UserTable.id
        }
    }

    override fun findUser(id: Int): User {
        DataBaseUtil.connectDatabase()

        return transaction {
            val userRow = UserTable.select { UserTable.id eq id }.single()
            User(userRow[UserTable.id], userRow[UserTable.name], userRow[UserTable.email], userRow[UserTable.password])
        }
    }

    override fun findUser(email: String): User {
        DataBaseUtil.connectDatabase()

        return transaction {
            val userRow = UserTable.select{ UserTable.email eq email }.single()
            User(userRow[UserTable.id], userRow[UserTable.name], userRow[UserTable.email], userRow[UserTable.password])
        }
    }
}