package com.example.movieapp.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.data.locale.room.UserDatabase
import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.preferences.LoginPreferences
import com.example.movieapp.ui.login.LoginViewModel
import com.example.movieapp.ui.movielist.MovieListViewModel
import com.example.movieapp.ui.profile.ProfileViewModel
import com.example.movieapp.ui.register.RegisterViewModel

class ViewModelFactory (application: Application) :
    ViewModelProvider.Factory {

    private val userDao = UserDatabase.getDatabase(application).userDao()

    private val pref = LoginPreferences(application)

    private val repository = UserRepository(userDao, pref)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel(repository) as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel(repository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository) as T
            modelClass.isAssignableFrom(MovieListViewModel::class.java) -> MovieListViewModel(repository) as T
            else -> throw Throwable("ViewModel not Valid")
        }
    }
}