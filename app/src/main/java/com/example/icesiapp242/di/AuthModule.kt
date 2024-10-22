package com.example.icesiapp242.di

import com.example.icesiapp242.repository.AuthRepository
import com.example.icesiapp242.repository.AuthRepositoryImpl
import com.example.icesiapp242.repository.ChatRepository
import com.example.icesiapp242.repository.ChatRepositoryImpl
import com.example.icesiapp242.service.AuthService
import com.example.icesiapp242.service.AuthServiceImpl
import com.example.icesiapp242.service.ChatService
import com.example.icesiapp242.service.ChatServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AuthModule {

    @Provides
    fun provideAuthService(): AuthService {
        return AuthServiceImpl()
    }

    @Provides
    fun provideAuthRepository(authService: AuthService): AuthRepository {
        return AuthRepositoryImpl(authService)
    }
}