package com.example.icesiapp242.repository

import com.example.icesiapp242.domain.model.User
import com.example.icesiapp242.service.UserServices
import com.example.icesiapp242.service.UserServicesImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

interface UserRepository {
    suspend fun createUser(user: User)
    suspend fun getCurrentUser():User?
}

class UserRepositoryImpl @Inject constructor(
    val userServices: UserServices
):UserRepository{
    override suspend fun createUser(user: User) {
        userServices.createUser(user)
    }

    override suspend fun getCurrentUser(): User? {
        Firebase.auth.currentUser?.let {
            return userServices.getUserById(it.uid)
        } ?: run {
           return null
        }
    }
}