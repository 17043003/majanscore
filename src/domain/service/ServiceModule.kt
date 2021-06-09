package com.msyiszk.domain.service

import com.msyiszk.application.service.UserServiceImpl
import com.msyiszk.application.service.UserServiceImplTest
import com.msyiszk.domain.repository.UserRepository
import com.msyiszk.infrastructure.UserRepositoryImpl
import org.koin.dsl.module

val userModule = module {
    single<UserService> { UserServiceImpl(get()) }
    single<UserRepository>{ UserRepositoryImpl() }
}

val testModule = module {
    single<UserService> { UserServiceImplTest(get()) }
    single<UserRepository>{ UserRepositoryImpl() }
}