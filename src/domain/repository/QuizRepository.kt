package com.msyiszk.domain.repository

import com.msyiszk.domain.model.Quiz
import com.msyiszk.presentation.form.QuizRespond
import com.msyiszk.presentation.form.RegisterQuizRequest

interface QuizRepository {
    fun getAllQuiz(): List<Quiz>
    fun registerQuiz(quiz: RegisterQuizRequest): Int
}