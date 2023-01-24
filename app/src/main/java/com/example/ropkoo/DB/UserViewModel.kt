package com.example.ropkoo.DB

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>

    //val login: LiveData<List<User>>
    val repository: UserRepository

    init {
        val userDAO = UserDatabase.getDatabase(application).userDAO()
        repository = UserRepository(userDAO)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

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

    fun getLogin(name: String, password: String) : Deferred<List<User>> {
        return viewModelScope.async(Dispatchers.IO) {
            repository.getLogin(name, password)
        }
    }


    /*fun addProgress(progress: Progress) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addProgress(progress)
        }
    }*/
}