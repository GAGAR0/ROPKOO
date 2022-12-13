package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class myProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        var back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }
    }
}