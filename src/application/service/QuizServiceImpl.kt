package com.msyiszk.application.service

import com.msyiszk.domain.model.Quiz
import com.msyiszk.domain.repository.QuizRepository
import com.msyiszk.domain.service.QuizService
import com.msyiszk.presentation.form.RegisterQuizRequest

class QuizServiceImpl(
    private val quizRepository: QuizRepository
): QuizService {
    override fun getAllQuiz(): List<Quiz> {
        return quizRepository.getAllQuiz()
    }
    override fun registerQuiz(request: RegisterQuizRequest): Int {
        return quizRepository.registerQuiz(request)
    }

    override fun getDetailQuiz(id: Int): Quiz {
        return quizRepository.getDetailQuiz(id)
    }
}