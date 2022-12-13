package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class stepCounter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step_counter)

        var back = findViewById<ImageButton>(R.id.back)
        back.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }
    }
}