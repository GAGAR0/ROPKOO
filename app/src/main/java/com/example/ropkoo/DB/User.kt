package com.example.ropkoo.DB

import androidx.room.*
import com.example.ropkoo.bodyInputFragment1
import com.example.ropkoo.bodyInputFragment2
import java.util.Locale.Category
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val user_id: Int?,
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val weight: Float,
    val height: Int
    )

@Entity(tableName = "goal_table")
data class Goals(
    @PrimaryKey(autoGenerate = true)
    val goals_id: Int?,
    val userId: Int?,
    val bodyInputFragment1:  Int,
    val bodyInputFragment2: Int
)

@Entity(tableName = "progress_table")
data class Progress(
    @PrimaryKey(autoGenerate = true)
    val progress_id: Int?,
    val userId: Int?,
    val dailyCalories: Int,
    val stepCount: Int,
    val waterIntake: Int,
    val goalProgressPercentage: Int,
    val dailyWeight: Float,
    val weeklyWeight: Float
)

data class UserWithGoals(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "userId",
        entity = Goals::class
    )
    val goal: Goals
)

data class UserWithProgress(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "userId",
        entity = Progress::class
    )
    val progress: Progress
)


    /*val gender: ,
    val goalsId:,
    val progressId:,*/
