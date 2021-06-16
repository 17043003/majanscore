package com.msyiszk.presentation.form

data class RegisterQuizRequest(
    val title: String,
    val content: String,
    val user_id: Int
)