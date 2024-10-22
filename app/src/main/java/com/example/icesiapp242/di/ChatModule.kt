package com.example.icesiapp242.di

import com.example.icesiapp242.repository.ChatRepository
import com.example.icesiapp242.repository.ChatRepositoryImpl
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ChatModule {

    @Provides
    fun provideChatService(): ChatService {
        return ChatServiceImpl()
    }

    @Provides
    fun provideChatRepository(chatService: ChatService): ChatRepository {
        return ChatRepositoryImpl(chatService)
    }
}