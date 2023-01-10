package com.example.movieapp.data.locale.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieapp.data.locale.model.User

@Dao
interface UserDao {
    @Insert
    fun register(user: User)

    @Query("select * from user where email like :email and password like :password")
    suspend fun getUser(email: String, password: String): User?

    @Query("select * from user where email like :email")
    suspend fun getUserByEmail(email: String): User?

}