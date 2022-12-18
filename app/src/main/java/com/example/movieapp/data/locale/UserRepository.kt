package com.example.movieapp.data.locale

import com.example.movieapp.data.locale.model.User
import com.example.movieapp.data.locale.preferences.LoginPreferences
import com.example.movieapp.data.locale.room.UserDao
import com.example.movieapp.helper.TextMessage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val pref: LoginPreferences
) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun register(user: User) = executorService.execute { userDao.register(user) }

    suspend fun login(user: User): TextMessage {
        userDao.getUser(user.email, user.password)
            ?: return TextMessage.WrongEmailOrPassword

        pref.isLogin = true
        pref.loginEmail = user.email
        return TextMessage.Ok
    }

    suspend fun getUserByEmail(email: String) = userDao.getUserByEmail(email)

    fun getEmail() = pref.loginEmail

    fun checkLogin(): Boolean = pref.isLogin

    fun logout() {
        pref.isLogin = false
        pref.loginEmail = ""
    }
}