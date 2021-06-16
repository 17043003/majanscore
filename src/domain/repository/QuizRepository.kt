package com.msyiszk.domain.repository

import com.msyiszk.presentation.form.RegisterQuizRequest

interface QuizRepository {
    fun registerQuiz(quiz: RegisterQuizRequest): Int
}