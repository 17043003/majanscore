package com.msyiszk.domain.repository

import com.msyiszk.domain.model.User
import com.msyiszk.presentation.form.RegisterUserRequest

interface UserRepository {
    fun register(name: String, email: String): Int

    fun findUser(id: Int): User
    fun findUser(email: String): User
}