package com.example.movieapp.injection

import android.content.Context
import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.preferences.LoginPreferences
import com.example.movieapp.data.locale.room.UserDao
import com.example.movieapp.data.locale.room.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelProviders {
    @Provides
    @Singleton
    fun provideRepository(userDao: UserDao, pref: LoginPreferences) = UserRepository(userDao, pref)
}