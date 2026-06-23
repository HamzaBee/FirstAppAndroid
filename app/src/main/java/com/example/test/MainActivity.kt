package com.example.test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnApp1 = findViewById<Button>(R.id.btnApp1)
        val btnApp2 = findViewById<Button>(R.id.btnApp2)
        val btnApp3 = findViewById<Button>(R.id.btnApp3)

        btnApp1.setOnClickListener {
            startActivity(Intent(this, CurrencyActivity::class.java))
        }

        btnApp2.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        btnApp3.setOnClickListener {
            startActivity(Intent(this, ImcActivity::class.java))
        }
    }
}