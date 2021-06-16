package com.msyiszk.domain.service

import com.msyiszk.domain.model.Quiz
import com.msyiszk.presentation.form.RegisterQuizRequest

interface QuizService {
    fun registerQuiz(request: RegisterQuizRequest): Int
}