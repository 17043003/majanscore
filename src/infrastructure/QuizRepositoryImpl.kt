package com.msyiszk.infrastructure

import com.msyiszk.domain.repository.QuizRepository
import com.msyiszk.infrastructure.DataBaseUtil
import com.msyiszk.presentation.form.RegisterQuizRequest
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class QuizRepositoryImpl: QuizRepository {
    override fun registerQuiz(quiz: RegisterQuizRequest): Int {
        DataBaseUtil.connectDatabase()


        return transaction {
            addLogger(StdOutSqlLogger)

            UserTable.select{ UserTable.id eq quiz.user_id }.singleOrNull() ?: return@transaction 0
            QuizTable.insert{
                it[this.title] = quiz.title
                it[this.content] = quiz.content
                it[this.user_id] = quiz.user_id
            } get QuizTable.id
        }
    }
}