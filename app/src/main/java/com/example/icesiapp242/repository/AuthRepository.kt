package com.example.icesiapp242.repository

import android.content.Context
import androidx.credentials.CredentialManager
import com.example.icesiapp242.domain.model.User
import com.example.icesiapp242.service.AuthService
import com.example.icesiapp242.service.AuthServiceImpl
import com.example.icesiapp242.util.CredentialManagerHelper
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

interface AuthRepository {
    suspend fun signup(user:User, password:String)
    suspend fun signupWithGoogle(credentialManagerHelper: CredentialManagerHelper)
}

class AuthRepositoryImpl(
    val authService: AuthService = AuthServiceImpl(),
    val userRepository: UserRepository = UserRepositoryImpl()
) : AuthRepository{
    override suspend fun signup(user: User, password: String) {
        //1. Registro en modulo de autenticación
        authService.createUser(user.email, password)
        //2. Obtenemos el UID
        val uid = Firebase.auth.currentUser?.uid
        //3. Crear el usuario en Firestore
        uid?.let {
            user.id = it
            userRepository.createUser(user)
        }
    }

    override suspend fun signupWithGoogle(credentialManagerHelper: CredentialManagerHelper) {
        try {
            val token = authService.getGoogleToken(credentialManagerHelper)
            authService.registerGoogleUserInFirebase(token)
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }
}

