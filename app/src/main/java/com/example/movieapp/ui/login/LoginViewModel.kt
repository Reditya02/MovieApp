package com.example.movieapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.model.User
import com.example.movieapp.helper.TextChecker
import com.example.movieapp.helper.TextMessage
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val edtEmailResponse = MutableLiveData<TextMessage>()
    fun checkEdtEmail(email: String): LiveData<TextMessage> {
        edtEmailResponse.value = TextChecker.checkEmail(email)
        return edtEmailResponse
    }

    private val edtPasswordResponse = MutableLiveData<TextMessage>()
    fun checkEdtPassword(password: String): LiveData<TextMessage> {
        edtPasswordResponse.value = TextChecker.checkPassword(password)
        return edtPasswordResponse
    }

    private val isLoggedIn = MutableLiveData<Boolean>()
    fun isAlreadyLogin(): LiveData<Boolean> {
        isLoggedIn.value = repository.checkLogin()
        return isLoggedIn
    }

    fun getEmail() = repository.getEmail()

    private val loginStatus = MutableLiveData<TextMessage>()
    fun login(user: User): LiveData<TextMessage> {
        viewModelScope.launch {
            val res = repository.login(user)
            loginStatus.value = res
        }

        return loginStatus
    }
}