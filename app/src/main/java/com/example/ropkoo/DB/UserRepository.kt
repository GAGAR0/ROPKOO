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

    suspend fun updateWeight(progress: Progress){
        userDAO.updateWeight(progress)
    }

    suspend fun addSession(session: Session){
        userDAO.addSession(session)
    }

    suspend fun updateSession(session: Session){
        userDAO.updateSession(session)
    }

    fun getLogin(name: String, password: String): LiveData<List<User>>? {
        return userDAO.getLogin(name, password)
    }

    fun getUserFromID(user_id: Int): LiveData<List<User>>? {
        return userDAO.getUserFromID(user_id)
    }

    fun getCurrentSession(): LiveData<List<Session>>? {
        return userDAO.getCurrentSession()
    }

    fun getProgress(user_id: Int): LiveData<List<Progress>>? {
        return userDAO.getProgress(user_id)
    }

    /*fun updateWeight(currentWeight: Float, user_id: Int){
        userDAO.updateWeight(Progress)
    }*/

    /*fun getGoalWithUsers(goal: Int): List<GoalWithUsers>{
        return userDAO.getGoalWithUsers(goal)
    }*/


   /* suspend fun addProgress(progress: Progress){
        userDAO.addProgress(progress)
    }*/
}