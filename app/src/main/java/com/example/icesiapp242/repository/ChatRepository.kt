package com.example.icesiapp242.repository

import android.net.Uri
import android.util.Log
import com.example.icesiapp242.domain.model.Message
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

interface ChatRepository {
    suspend fun sendMessage(message: Message, uri: Uri?, otherUserId: String)
    suspend fun getMessages(otherUserId: String): List<Message?>
    suspend fun getLiveMessages(otherUserId: String, callback: suspend (Message) -> Unit)
    suspend fun getChatID(otherUserID: String): String
}

class ChatRepositoryImpl(
    val chatService: ChatService = ChatServiceImpl()
) : ChatRepository {
    override suspend fun sendMessage(message: Message, uri: Uri?, otherUserId: String) {
        uri?.let {
            val imageID = UUID.randomUUID().toString()
            message.imageId = imageID
            chatService.sendImage(it, imageID)
        }
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        chatService.sendMessage(message, chatID)
    }

    override suspend fun getMessages(otherUserId: String): List<Message?> {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        val messages = chatService.getMessages(chatID)
        messages.forEach { message ->
            message?.imageId?.let {
                message.imageUrl = chatService.getURLOfImage(it)
            }
        }
        return messages
    }

    override suspend fun getLiveMessages(otherUserId: String, callback: suspend (Message) -> Unit) {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        chatService.getLiveMessages(chatID) { doc ->
            val msg = doc.toObject(Message::class.java)
            msg.imageId?.let {
                msg.imageUrl = chatService.getURLOfImage(it)
            }
            callback(msg)
        }
    }

    override suspend fun getChatID(otherUserID: String): String {
        return chatService.searchChatId(otherUserID, Firebase.auth.currentUser?.uid ?: "")
    }

}