package com.example.icesiapp242.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp242.domain.model.Message
import com.example.icesiapp242.domain.model.User

import com.example.icesiapp242.repository.UserRepository
import com.example.icesiapp242.repository.UserRepositoryImpl
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProfileViewModel(
    val userRepository: UserRepository = UserRepositoryImpl(),
) : ViewModel() {

    private val _user = MutableLiveData(User())
    val user: LiveData<User?> get() = _user

    private val _userList = MutableLiveData(listOf<User?>())
    val userList: LiveData<List<User?>> get() = _userList

    fun getCurrentUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val me = userRepository.getCurrentUser()
            withContext(Dispatchers.Main) {
                _user.value = me
            }
        }
    }

    fun getUserList() {
        viewModelScope.launch(Dispatchers.IO) {
            val userList = userRepository.getAllUsers()
            withContext(Dispatchers.Main) {
                _userList.value = userList
            }
        }
    }

}