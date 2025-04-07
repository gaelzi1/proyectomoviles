package com.example.proyecto1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class RecentQueriesActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var editText: EditText
    private lateinit var searchIcon: ImageView
    private lateinit var queriesLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consult)

        db = FirebaseFirestore.getInstance()
        editText = findViewById(R.id.editTextConsulta)
        searchIcon = findViewById(R.id.iconoLupa)
        queriesLayout = findViewById(R.id.layoutConsultas)

        // Cargar consultas existentes
        obtenerConsultas()

        searchIcon.setOnClickListener {
            val consultaTexto = editText.text.toString().trim()
            if (consultaTexto.isNotEmpty()) {
                guardarConsulta(consultaTexto)
                editText.setText("")
            }
        }
    }

    private fun guardarConsulta(texto: String) {
        val consulta = mapOf(
            "texto" to texto,
            "fecha" to System.currentTimeMillis()
        )

        db.collection("consultas")
            .add(consulta)
            .addOnSuccessListener {
                obtenerConsultas() // Recarga la lista después de guardar
            }
    }

    private fun obtenerConsultas() {
        db.collection("consultas")
            .orderBy("fecha", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                queriesLayout.removeAllViews()
                for (document in result) {
                    val texto = document.getString("texto") ?: ""
                    val textView = TextView(this).apply {
                        text = texto
                        setTextColor(resources.getColor(android.R.color.white))
                        textSize = 16f
                    }
                    queriesLayout.addView(textView)
                }
            }
    }
}