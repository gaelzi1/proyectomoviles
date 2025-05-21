package com.example.proyecto1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_init)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recentQueriesLink = findViewById<TextView>(R.id.recentQueriesLink)
        recentQueriesLink.setOnClickListener {
            val intent = Intent(this, RecentQueriesActivity::class.java)
            startActivity(intent)
        }
        val profileButton: Button = findViewById(R.id.btnProfile)

        profileButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        val btnPreguntaLegal = findViewById<Button>(R.id.btnPreguntaLegal)
        btnPreguntaLegal.setOnClickListener {
            val intent = Intent(this, PreguntaLegalActivity::class.java)
            startActivity(intent)
        }


    }


}