package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class bodyInput2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_input2)

        var btn_goal1 = findViewById<Button>(R.id.btn_goal1)
        btn_goal1.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }

        var btn_goal2 = findViewById<Button>(R.id.btn_goal2)
        btn_goal2.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }

        var btn_goal3 = findViewById<Button>(R.id.btn_goal3)
        btn_goal3.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }

        var btn_goal4 = findViewById<Button>(R.id.btn_goal4)
        btn_goal4.setOnClickListener{
            val intent = Intent(this, mainMenu::class.java)
            startActivity(intent)
        }
    }
}