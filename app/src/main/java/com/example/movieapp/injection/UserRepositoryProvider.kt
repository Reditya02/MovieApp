package com.example.movieapp.injection

import android.content.Context
import com.example.movieapp.data.locale.preferences.LoginPreferences
import com.example.movieapp.data.locale.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UserRepositoryProvider {
    @Provides
    @Singleton
    fun provideLoginPreferences(@ApplicationContext context: Context) = LoginPreferences(context)

    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao()

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context) = UserDatabase.getDatabase(context)
}