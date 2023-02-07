package com.example.ropkoo.DB

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.*
import com.example.ropkoo.bodyInputFragment1
import com.example.ropkoo.bodyInputFragment2
import java.util.Locale.Category
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table", foreignKeys = [ForeignKey(entity = Goals::class, parentColumns = ["goals_id"], childColumns = ["goals_id"])])
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id") val user_id: Int?,
    @ColumnInfo(name = "goals_id") val goals_id: Int?,
    val name: String?,
    val email: String?,
    val password: String?,
    val gender: String?,
    val age: Int?,
    val weight: Float?,
    val height: Int?
    ) : Parcelable
@Parcelize
@Entity(tableName = "goal_table")
data class Goals(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "goals_id") val goals_id: Int?,
    val bodyInputFragment1:  String,
    val bodyInputFragment2: String
): Parcelable

@Parcelize
@Entity(tableName = "progress_table", foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["userId"])])
data class Progress(
    @PrimaryKey(autoGenerate = true)
    val progress_id: Int?,
    @ColumnInfo(name = "userId")val userId: Int?,
    val dailyCalories: Int,
    val stepCount: Int,
    val waterIntake: Int,
    val goalProgressPercentage: Int,
    val dailyWeight: Float,
    val weeklyWeight: Float
): Parcelable

@Entity(tableName = "currentsession_table", foreignKeys = [ForeignKey(entity = User::class, parentColumns = ["user_id"], childColumns = ["loggedUser_id"])])
data class Session(
    @PrimaryKey(autoGenerate = false)
    val session_id: Int?,
    @ColumnInfo(name = "loggedUser_id")val loggedUser_id: Int?
)

/*data class UserWithGoals(
    @Embedded val goal: Goals,
    @Relation(
        parentColumn = "goals_id",
        entityColumn = "goals_id"
    )
    val user: List<User>
)*/
/*@Entity
data class UserWithGoals(
    @Embedded val user: User,
    @Relation(
        parentColumn = "goals_id",
        entityColumn = "goals_id",
        entity = Goals::class
    )
    val goals: List<Goals>
)*/

/*data class GoalWithUsers (
    @Embedded var goal: Goals,
    @Relation(
        parentColumn = "goals_id",
        entityColumn = "goals_id"
    )
    @Embedded var user: List<User>
)

data class UserAndProgress(
    @Embedded val user: User,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "userId"
    )
    val progress: Progress
)*/


    /*val gender: ,
    val goalsId:,
    val progressId:,*/
