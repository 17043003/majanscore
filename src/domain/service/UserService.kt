package com.msyiszk.domain.service

import com.msyiszk.domain.model.User
import com.msyiszk.presentation.form.RegisterUserRequest

interface UserService {
    fun getCurrentUser(id: Int): User

    fun registerUser(request: RegisterUserRequest): Int
}