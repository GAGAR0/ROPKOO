package com.example.ropkoo.DB

import android.database.Cursor
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addGoals(goal: Goals)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProgress(progress: Progress)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addSession(session: Session)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateSession(session: Session)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateWeight(progress: Progress)


   // @Update(onConflict = OnConflictStrategy.REPLACE)
    //fun setForeignKeyGoals(@ColumnInfo(name = "goals_id") goalsId: Long, user: User)

    /*@Update(onConflict = OnConflictStrategy.REPLACE)
    fun setForeignKeyProgress(userId: Progress)*/
   /* @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addProgress(progress: Progress)*/

    @Query("SELECT * FROM user_table ORDER BY user_id ASC")
    fun readAllData(): LiveData<List<User>>

   /* @Insert
    fun createUser(user: User);*/
    @Query("SELECT * FROM user_table WHERE name = :name AND password = :password")
    fun getLogin(name: String, password: String): LiveData<List<User>>?

    @Query("SELECT * FROM user_table WHERE user_id = :user_id")
    fun getUserFromID(user_id: Int): LiveData<List<User>>

    @Query("SELECT * FROM currentsession_table")
    fun getCurrentSession(): LiveData<List<Session>>

    @Query("SELECT * FROM progress_table WHERE userId = :user_id")
    fun getProgress(user_id: Int): LiveData<List<Progress>>?

    @Query("UPDATE progress_table SET dailyWeight = :currentWeight WHERE userId = :user_id")
    fun updateWeight(currentWeight: Float, user_id: Int)


    /*@Transaction
    @Query("SELECT * FROM user_table JOIN goal_table ON user_table.goals_id = goal_table.goals_id")
    fun getUserWithGoals(): List<UserWithGoals>*/

    /*@Transaction
    @Query("SELECT * FROM goal_table WHERE goals_id = :goal")
    fun getGoalWithUsers(goal: Int): List<GoalWithUsers>

    @Transaction
    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUsersAndProgress(id: Int): List<UserAndProgress>*/

    /*@Query("INSERT INTO goal_table(userID) VALUES (:user_id)")
    fun connectUserToGoal(user_id: Int)*/
}