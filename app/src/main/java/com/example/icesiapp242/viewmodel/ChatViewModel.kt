package com.example.icesiapp242.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.icesiapp242.domain.model.Message
import com.example.icesiapp242.repository.ChatRepository
import com.example.icesiapp242.repository.ChatRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    val chatRepository: ChatRepository
) : ViewModel() {

    private val _messagesState = MutableLiveData<List<Message?>>()
    val messagesState: LiveData<List<Message?>> get() = _messagesState

    fun getMessages(otherUserID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val messages = chatRepository.getMessages(otherUserID)
            withContext(Dispatchers.Main) { _messagesState.value = messages }
        }
    }

    fun sendMessage(content: String, otherUserID: String) {
        val message = Message(
            UUID.randomUUID().toString(),
            content
        )
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.sendMessage(message, otherUserID)
        }
    }

    fun getMessagesLiveMode(otherUserID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getLiveMessages(otherUserID) { message ->
                Log.e(">>>", message.content)
                val currentMessages = _messagesState.value ?: arrayListOf()
                val updatedMessages = ArrayList(currentMessages) // Crear una copia
                updatedMessages.add(message)
                _messagesState.value = updatedMessages
            }
        }

    }

}