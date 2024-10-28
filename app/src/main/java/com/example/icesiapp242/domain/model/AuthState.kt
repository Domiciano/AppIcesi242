package com.example.icesiapp242.domain.model

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Error(var message:String) : AuthState()
    object Success: AuthState()
}