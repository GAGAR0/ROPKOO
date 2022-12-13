package com.example.ropkoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class bodyInput1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body_input1)

        var btn_current1 = findViewById<Button>(R.id.btn_current1)
        btn_current1.setOnClickListener{
            val intent = Intent(this, bodyInput2::class.java)
            startActivity(intent)
        }

        var btn_current2 = findViewById<Button>(R.id.btn_current2)
        btn_current2.setOnClickListener{
            val intent = Intent(this, bodyInput2::class.java)
            startActivity(intent)
        }

        var btn_current3 = findViewById<Button>(R.id.btn_current3)
        btn_current3.setOnClickListener{
            val intent = Intent(this, bodyInput2::class.java)
            startActivity(intent)
        }

        var btn_current4 = findViewById<Button>(R.id.btn_current4)
        btn_current4.setOnClickListener{
            val intent = Intent(this, bodyInput2::class.java)
            startActivity(intent)
        }
    }
}