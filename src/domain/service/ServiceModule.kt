package com.msyiszk.domain.service

import com.msyiszk.application.service.QuizServiceImpl
import com.msyiszk.application.service.UserServiceImpl
import com.msyiszk.application.service.UserServiceImplTest
import com.msyiszk.domain.repository.QuizRepository
import com.msyiszk.domain.repository.UserRepository
import com.msyiszk.infrastructure.QuizRepositoryImpl
import com.msyiszk.infrastructure.UserRepositoryImpl
import org.koin.dsl.module

val userModule = module {
    single<UserService> { UserServiceImpl(get()) }
    single<UserRepository>{ UserRepositoryImpl() }

    single<QuizService> { QuizServiceImpl(get()) }
    single<QuizRepository> { QuizRepositoryImpl() }
}

val testModule = module {
    single<UserService> { UserServiceImplTest(get()) }
    single<UserRepository>{ UserRepositoryImpl() }

    single<QuizService> { QuizServiceImpl(get()) }
    single<QuizRepository> { QuizRepositoryImpl() }
}