package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class mainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        var btn_main1 = findViewById<Button>(R.id.btn_main1)
        btn_main1.setOnClickListener {
            val intent = Intent(this, calorieCalculator::class.java)
            startActivity(intent)
        }

        var btn_main2 = findViewById<Button>(R.id.btn_main2)
        btn_main2.setOnClickListener {
            val intent = Intent(this, goalProgress::class.java)
            startActivity(intent)
        }

        var btn_main3 = findViewById<Button>(R.id.btn_main3)
        btn_main3.setOnClickListener {
            val intent = Intent(this, stepCounter::class.java)
            startActivity(intent)
        }

        var btn_main4 = findViewById<Button>(R.id.btn_main4)
        btn_main4.setOnClickListener {
            val intent = Intent(this, hydration::class.java)
            startActivity(intent)
        }
    }
}