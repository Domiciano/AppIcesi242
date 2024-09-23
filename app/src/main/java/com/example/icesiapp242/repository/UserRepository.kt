package com.example.icesiapp242.repository

import com.example.icesiapp242.domain.model.User
import com.example.icesiapp242.service.UserServices
import com.example.icesiapp242.service.UserServicesImpl

interface UserRepository {
    suspend fun createUser(user: User)
}

class UserRepositoryImpl(
    val userServices: UserServices = UserServicesImpl()
):UserRepository{
    override suspend fun createUser(user: User) {
        userServices.createUser(user)
    }

}