package com.example.stevdza_sanbdpractice.data.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stevdza_sanbdpractice.data.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>
}