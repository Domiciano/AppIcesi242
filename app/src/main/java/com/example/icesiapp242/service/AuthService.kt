package com.example.icesiapp242.service

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import com.example.icesiapp242.util.CredentialManagerHelper
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

interface AuthService {
    suspend fun createUser(email: String, password: String)
    suspend fun getGoogleToken(credentialManagerHelper:CredentialManagerHelper):String
    suspend fun registerGoogleUserInFirebase(googleToken:String)
}

class AuthServiceImpl(
) : AuthService {

    override suspend fun createUser(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password).await()
    }

    override suspend fun getGoogleToken(credentialManagerHelper:CredentialManagerHelper): String {
        return credentialManagerHelper.getGoogleToken()
    }

    override suspend fun registerGoogleUserInFirebase(googleIdToken: String) {
        val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
        Firebase.auth.signInWithCredential(credential).await()
    }
}