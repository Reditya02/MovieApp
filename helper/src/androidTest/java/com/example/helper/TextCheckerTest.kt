package com.example.helper

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helper.TextChecker.checkPassword
import com.example.helper.TextChecker.checkUsername
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextCheckerTest {
    @Test
    fun checkPasswordLess8Character() {
        val result = checkPassword("halo")
        Assert.assertEquals(TextMessage.PasswordTooShort, result)
    }

    @Test
    fun checkUsernameEmpty() {
        val result = checkUsername("")
        Assert.assertEquals(TextMessage.TextEmpty, result)
    }
}