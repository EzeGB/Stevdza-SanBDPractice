package com.example.stevdza_sanbdpractice.data.repositories

import androidx.lifecycle.LiveData
import com.example.stevdza_sanbdpractice.data.daos.UserDao
import com.example.stevdza_sanbdpractice.data.models.User

class UserRepository (private val userDao: UserDao){

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}