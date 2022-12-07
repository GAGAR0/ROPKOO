package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn_register = findViewById<Button>(R.id.btn_register)
        btn_register.setOnClickListener{
            val intent = Intent(this, createAccount::class.java)
            startActivity(intent)
        }

        var btn_signIn = findViewById<Button>(R.id.btn_signIn)
        btn_signIn.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }
    }
}