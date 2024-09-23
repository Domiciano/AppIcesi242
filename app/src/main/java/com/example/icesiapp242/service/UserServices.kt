package com.example.icesiapp242.service

import com.example.icesiapp242.domain.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface UserServices {
    suspend fun createUser(user: User)
}

class UserServicesImpl:UserServices{
    override suspend fun createUser(user: User) {
        Firebase.firestore
            .collection("users")
            .document(user.id)
            .set(user)
            .await()
    }

}
