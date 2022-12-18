package com.example.movieapp.ui.profile

import androidx.lifecycle.ViewModel
import com.example.movieapp.data.locale.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun logout() {
        repository.logout()
    }
}