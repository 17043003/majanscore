package com.msyiszk.presentation.form

import com.msyiszk.domain.model.User

data class UserInfo(
    val id: Int,
    val name: String,
    val email: String
){
    constructor(model: User): this(model.id, model.name, model.email)
}

data class RegisterUserRequest(
    val name: String,
    val email: String,
    val password: String
)