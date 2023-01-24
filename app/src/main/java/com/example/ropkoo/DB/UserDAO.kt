package com.example.ropkoo.DB

import android.database.Cursor
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addGoals(goal: Goals)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProgress(progress: Progress)

   /* @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProgress(progress: Progress)*/

    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    fun readAllData(): LiveData<List<User>>

   /* @Insert
    fun createUser(user: User);*/

    @Query("SELECT * FROM user_table WHERE name LIKE :name AND password LIKE :password")
    fun getLogin(name: String, password: String): List<User>

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithGoals(): List<UserWithGoals>

    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUsersWithProgress(): List<UserWithProgress>

    /*@Query("INSERT INTO goal_table(userID) VALUES (:user_id)")
    fun connectUserToGoal(user_id: Int)*/
}