package com.msyiszk.domain.model

import com.msyiszk.infrastructure.QuizTable

data class Quiz(
    val id: Int,
    val title: String,
    val content: String,
    val user_name: String,
    val drawn: String,
    val round: Int,
    val wind: Int,
    val around: Int,
    val dora: String,
    val point: Int,
    val answer: String,
    val description: String
)
