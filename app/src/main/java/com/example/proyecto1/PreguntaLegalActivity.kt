package com.example.proyecto1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PreguntaLegalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pregunta_legal)

        val etPregunta = findViewById<EditText>(R.id.etPregunta)
        val btnEnviar = findViewById<Button>(R.id.btnEnviarPregunta)
        val tvRespuesta = findViewById<TextView>(R.id.tvRespuesta)
        val btnPerfil = findViewById<ImageButton>(R.id.btnPerfil)

        btnEnviar.setOnClickListener {
            val textoPregunta = etPregunta.text.toString()
            if (textoPregunta.isNotEmpty()) {
                val pregunta = Pregunta(texto = textoPregunta)

                RetrofitClient.instance.buscarRespuesta(pregunta).enqueue(object : Callback<Respuesta> {
                    override fun onResponse(call: Call<Respuesta>, response: Response<Respuesta>) {
                        if (response.isSuccessful) {
                            val resultado = response.body()
                            tvRespuesta.text = resultado?.respuesta ?: "Respuesta vacía."
                        } else {
                            tvRespuesta.text = "Error en la respuesta del servidor."
                        }
                    }

                    override fun onFailure(call: Call<Respuesta>, t: Throwable) {
                        tvRespuesta.text = "Error de conexión: ${t.message}"
                    }
                })
            } else {
                tvRespuesta.text = "Escribe una pregunta primero."
            }
        }

        // Aquí activas el botón para ir al perfil
        btnPerfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}
