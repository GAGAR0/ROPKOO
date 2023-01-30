package com.example.ropkoo.DB

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.example.ropkoo.DB.UserDAO
import kotlinx.coroutines.Deferred

class UserRepository(private val userDAO: UserDAO) {

    val readAllData: LiveData<List<User>> = userDAO.readAllData()

   //val getUserWithGoals: List<UserWithGoals> = userDAO.getUserWithGoals()

    suspend fun addUser(user: User){
        userDAO.addUser(user)
    }

    suspend fun addGoals(goal: Goals){
        userDAO.addGoals(goal)
    }

    suspend fun addProgress(progress: Progress){
        userDAO.addProgress(progress)
    }

    /*fun getUserWithGoals(): List<UserWithGoals> {
        return userDAO.getUserWithGoals()
    }*/

    fun getLogin(name: String, password: String): LiveData<List<User>>? {
        return userDAO.getLogin(name, password)
    }

    /*fun getGoalWithUsers(goal: Int): List<GoalWithUsers>{
        return userDAO.getGoalWithUsers(goal)
    }*/


   /* suspend fun addProgress(progress: Progress){
        userDAO.addProgress(progress)
    }*/
}