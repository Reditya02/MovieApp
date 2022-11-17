package com.example.movieapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.locale.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

    fun logout() {
        repository.logout()
    }
}