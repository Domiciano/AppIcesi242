package com.example.icesiapp242.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp242.domain.model.AuthState
import com.example.icesiapp242.domain.model.User
import com.example.icesiapp242.repository.AuthRepository
import com.example.icesiapp242.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.rpc.context.AttributeContext.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

//Hilt
class SignupViewModel(
    val repo: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    private var _authState:MutableLiveData<AuthState> = MutableLiveData(AuthState.Idle)
    val authState:LiveData<AuthState> get() = _authState

    //0. Idle
    //1. Loading
    //2. Error
    //3. Success

    fun signup(user: User, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _authState.value = AuthState.Loading }
            try {
                repo.signup(user, password)
                withContext(Dispatchers.Main) { _authState.value = AuthState.Success }
            } catch (ex: FirebaseAuthException) {
                withContext(Dispatchers.Main) { _authState.value = AuthState.Error(ex.message.toString()) }
                ex.printStackTrace()
            }
        }
    }

    fun signin(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                withContext(Dispatchers.Main) { _authState.value = AuthState.Loading }
                repo.signin(email, password)
                withContext(Dispatchers.Main) { _authState.value = AuthState.Success }
            }catch (ex: FirebaseAuthInvalidUserException){
                withContext(Dispatchers.Main) { _authState.value = AuthState.Error("Al parecer el usuario no existe en la base de datos") }
                ex.printStackTrace()
            }catch (ex: FirebaseAuthInvalidCredentialsException){
                withContext(Dispatchers.Main) { _authState.value = AuthState.Error("El correo suministrado no tiene el formato correcto") }
                ex.printStackTrace()
            }catch (ex:Exception){
                withContext(Dispatchers.Main) { _authState.value = AuthState.Error(ex.message.toString()) }
                ex.printStackTrace()
            }
        }
    }

    //FirebaseAuthInvalidUserException
    //FirebaseAuthInvalidCredentialsException

}