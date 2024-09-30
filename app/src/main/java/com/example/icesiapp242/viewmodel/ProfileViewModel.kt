package com.example.icesiapp242.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp242.domain.model.User

import com.example.icesiapp242.repository.UserRepository
import com.example.icesiapp242.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(
    val userRepository: UserRepository = UserRepositoryImpl()
) : ViewModel() {


    private val _user = MutableLiveData<User?>(User())
    val user: LiveData<User?> get() = _user

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val me = userRepository.getCurrentUser()
            withContext(Dispatchers.Main) {
                _user.value = me
            }
        }
    }


}