package com.example.ropkoo.DB

import android.app.Application
import android.database.Cursor
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    var userIdModel: MutableLiveData<String> = MutableLiveData()

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
    fun updateWeight(progress: Progress){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWeight(progress)
        }
    }

    fun addSession(session: Session) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSession(session)
        }
    }
    fun updateSession(session: Session){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSession(session)
        }
    }

    fun getLogin(name: String, password: String) : LiveData<List<User>>? {
            return repository.getLogin(name, password)
    }

    fun getUserFromID(user_id: Int) : LiveData<List<User>>? {
        return repository.getUserFromID(user_id)
    }

    fun getCurrentSession() : LiveData<List<Session>>? {
        return repository.getCurrentSession()
    }

    fun getProgress(user_id: Int): LiveData<List<Progress>>? {
        return repository.getProgress(user_id)
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