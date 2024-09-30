package com.example.icesiapp242.util

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder

class CredentialManagerHelper(
    private val context: Context
) {
    suspend fun getGoogleToken(): String {
        val credentialManager = CredentialManager.create(context)

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId("612300733454-hqv4kjlu8udnppavthfprb8k5eamjnsj.apps.googleusercontent.com")
            .build()

        val request: GetCredentialRequest = Builder()
            .addCredentialOption(googleIdOption)
            .build()

        val response = credentialManager.getCredential(
            request = request,
            context = context,
        )

        val credential = response.credential
        val googleIDTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        Log.e(">>>Display Name", googleIDTokenCredential.displayName.toString())
        return googleIDTokenCredential.idToken
    }
}
