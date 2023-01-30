package com.example.ropkoo.DB

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>

    //val getUserWithGoals: List<UserWithGoals>
    //var getUserWithGoals: List<UserWithGoals>

    val repository: UserRepository

    init {
        val userDAO = UserDatabase.getDatabase(application).userDAO()
        repository = UserRepository(userDAO)
        readAllData = repository.readAllData
        //getUserWithGoals = repository.getUserWithGoals()
    }

    /*fun getUserWithGoals(): Deferred<List<UserWithGoals>> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getUserWithGoals()
        }
    }*/

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    /*fun addUserWithGoals(userWithGoals: UserWithGoals) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserWithGoals(userWithGoals)
        }
    }*/

    fun addGoals(goal: Goals) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addGoals(goal)
        }
    }

    fun addProgress(progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProgress(progress)
        }
    }

    fun getLogin(name: String, password: String) : LiveData<List<User>>? {
            return repository.getLogin(name, password)
    }


   /* fun getGoalWithUsers(goal: Int) : Deferred<List<GoalWithUsers>> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getGoalWithUsers(goal)
        }
    }*/



    /*fun addProgress(progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProgress(progress)
        }
    }*/
}