package com.msyiszk.application.service

import com.msyiszk.domain.model.User
import com.msyiszk.domain.repository.UserRepository
import com.msyiszk.domain.service.UserService
import com.msyiszk.presentation.form.RegisterUserRequest
import java.lang.IllegalArgumentException

class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun getCurrentUser(id: Int): User {
        return userRepository.findUser(id)
    }

    override fun getUserByEmail(email: String): User {
        return userRepository.findUser(email)
    }

    override fun registerUser(request: RegisterUserRequest): Int {
        return userRepository.register(request)
    }
}

class UserServiceImplTest(
    private val userRepository: UserRepository
): UserService {
    override fun getCurrentUser(id: Int): User {
        return User(1, "test", "test@example.com", "password")
    }

    override fun getUserByEmail(email: String): User {
        return User(1, "test", "test@example.com", "password")
    }

    override fun registerUser(request: RegisterUserRequest): Int {
        return 1
    }
}