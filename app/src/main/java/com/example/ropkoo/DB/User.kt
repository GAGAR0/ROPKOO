package com.example.ropkoo.DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val age: Int,
    val startWeight: Float,
    val height: Int,
    /*val gender: ,
    val goalsId:,
    val progressId:,*/

)