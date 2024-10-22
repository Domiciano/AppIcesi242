package com.example.icesiapp242.di

import com.example.icesiapp242.repository.ChatRepository
import com.example.icesiapp242.repository.ChatRepositoryImpl
import com.example.icesiapp242.repository.UserRepository
import com.example.icesiapp242.repository.UserRepositoryImpl
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import com.example.icesiapp242.service.UserServices
import com.example.icesiapp242.service.UserServicesImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UserModule {

    @Provides
    fun provideUserService(): UserServices {
        return UserServicesImpl()
    }

    @Provides
    fun provideUserRepository(userServices: UserServices): UserRepository {
        return UserRepositoryImpl(userServices)
    }
}