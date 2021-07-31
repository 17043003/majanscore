package com.msyiszk.presentation.form

data class RegisterQuizRequest(
    val title: String,
    val content: String,
    val user_id: Int,
    val drawn: String,
    val round: Int,
    val wind: Int,
    val around: Int,
    val dora: String,
    val point: Int,
    val answer: String,
    val description: String
)

data class QuizRespond(
    val title: String,
    val content: String,
    val user_id: Int,
    val drawn: String,
    val round: Int,
    val wind: Int,
    val around: Int,
    val dora: String,
    val point: Int,
    val answer: String,
    val description: String
)