package com.example.movieapp.injection

import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.preferences.LoginPreferences
import com.example.movieapp.data.locale.room.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ViewModelProviders {
    @Provides
    @Singleton
    fun provideRepository(userDao: UserDao, pref: LoginPreferences) = UserRepository(userDao, pref)
}