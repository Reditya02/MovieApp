package com.example.movieapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.locale.UserRepository
import com.example.movieapp.data.locale.model.User
import com.example.helper.TextChecker
import com.example.helper.TextChecker.checkPasswordRetype
import com.example.helper.TextMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private val _edtEmailResponse = MutableLiveData<TextMessage>()
    val edtEmailResponse: LiveData<TextMessage> = _edtEmailResponse

    private val _edtPasswordResponse = MutableLiveData<TextMessage>()
    val edtPasswordResponse: LiveData<TextMessage> = _edtPasswordResponse

    private val _edtRetypePasswordResponse = MutableLiveData<TextMessage>()
    val edtRetypePasswordResponse: LiveData<TextMessage> = _edtRetypePasswordResponse

    private val _edtUsernameResponse = MutableLiveData<TextMessage>()
    val edtUsernameResponse: LiveData<TextMessage> = _edtUsernameResponse

    fun checkEdtUsername(username: String) {
        _edtUsernameResponse.postValue(TextChecker.checkUsername(username))
    }

    fun checkEdtEmail(email: String) {
        _edtEmailResponse.value = TextChecker.checkEmail(email)
    }

    fun checkEdtPassword(password: String) {
        _edtPasswordResponse.value = TextChecker.checkPassword(password)
    }

    fun checkEdtRetypePassword(password: String, retypePassword: String) {
        _edtRetypePasswordResponse.value = checkPasswordRetype(password, retypePassword)
    }

    fun register(user: User) {
        repository.register(user)
    }
}