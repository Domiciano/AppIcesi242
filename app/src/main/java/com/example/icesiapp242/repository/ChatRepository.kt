package com.example.icesiapp242.repository

import android.util.Log
import com.example.icesiapp242.domain.model.Message
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

interface ChatRepository {
    suspend fun sendMessage(message: Message, otherUserId:String)
    suspend fun getMessages(otherUserId: String):List<Message?>
    suspend fun getLiveMessages(otherUserId: String, callback: (Message) -> Unit)
    suspend fun getChatID(otherUserID: String):String
}
class ChatRepositoryImpl @Inject constructor(
    val chatService: ChatService
) : ChatRepository{
    override suspend fun sendMessage(message: Message, otherUserId: String) {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "" )
        Log.e(">>>", "ChatID destino: $chatID")
        chatService.sendMessage(message, chatID)
    }

    override suspend fun getMessages(otherUserId: String): List<Message?> {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "" )
        return chatService.getMessages(chatID)
    }

    override suspend fun getLiveMessages(otherUserId: String, callback: (Message) -> Unit) {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "" )
        chatService.getLiveMessages(chatID) { doc ->
            val msg = doc.toObject(Message::class.java)
            callback(msg)
        }
    }

    override suspend fun getChatID(otherUserID: String): String {
        return chatService.searchChatId(otherUserID, Firebase.auth.currentUser?.uid ?: "" )
    }

}