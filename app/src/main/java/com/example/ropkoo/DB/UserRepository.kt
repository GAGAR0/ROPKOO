package com.example.ropkoo.DB

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.ropkoo.DB.UserDAO

class UserRepository(private val userDAO: UserDAO) {

    val readAllData: LiveData<List<User>> = userDAO.readAllData()

    suspend fun addUser(user: User){
        userDAO.addUser(user)
    }

    suspend fun addGoals(goal: Goals){
        userDAO.addGoals(goal)
    }

    suspend fun addProgress(progress: Progress){
        userDAO.addProgress(progress)
    }

    fun getLogin(name: String, password: String): List<User>{
        return userDAO.getLogin(name, password)
    }
   /* suspend fun addProgress(progress: Progress){
        userDAO.addProgress(progress)
    }*/
}