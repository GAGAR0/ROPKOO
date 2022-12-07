package com.example.ropkoo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class createAccount : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        var btn_continue = findViewById<Button>(R.id.btn_continue)
        btn_continue.setOnClickListener{
            val intent = Intent(this, createProfile::class.java)
            startActivity(intent)
        }
    }
}