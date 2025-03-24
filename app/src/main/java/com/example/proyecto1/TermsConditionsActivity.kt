package com.example.proyecto1

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TermsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_terminos_condiciones)

        val btnClose: Button = findViewById(R.id.btn_close)
        btnClose.setOnClickListener {
            finish()
        }
    }
}