package com.msyiszk.infrastructure

import com.msyiszk.domain.model.Quiz
import com.msyiszk.domain.repository.QuizRepository
import com.msyiszk.infrastructure.DataBaseUtil
import com.msyiszk.presentation.form.QuizRespond
import com.msyiszk.presentation.form.RegisterQuizRequest
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import org.jetbrains.exposed.sql.transactions.transaction

class QuizRepositoryImpl: QuizRepository {
    override fun getAllQuiz(): List<Quiz> {
        DataBaseUtil.connectDatabase()

        return transaction {
            (QuizTable innerJoin UserTable).selectAll().map{
                Quiz(
                    it[QuizTable.id],
                    it[QuizTable.title],
                    it[QuizTable.content],
                    it[UserTable.name],
                    it[QuizTable.drawn],
                    it[QuizTable.round],
                    it[QuizTable.wind],
                    it[QuizTable.around],
                    it[QuizTable.dora],
                    it[QuizTable.point],
                    it[QuizTable.answer],
                    it[QuizTable.description]
                )
            }
        }
    }
    override fun registerQuiz(quiz: RegisterQuizRequest): Int {
        DataBaseUtil.connectDatabase()

        return transaction {
            addLogger(StdOutSqlLogger)

            UserTable.select{ UserTable.id eq quiz.user_id }.singleOrNull() ?: return@transaction 0
            QuizTable.insert{
                it[this.title] = quiz.title
                it[this.content] = quiz.content
                it[this.user_id] = quiz.user_id
                it[this.drawn] = quiz.drawn
                it[this.round] = quiz.round
                it[this.wind] = quiz.wind
                it[this.around] = quiz.around
                it[this.dora] = quiz.dora
                it[this.point] = quiz.point
                it[this.answer] = quiz.answer
                it[this.description] = quiz.description
            } get QuizTable.id
        }
    }
}