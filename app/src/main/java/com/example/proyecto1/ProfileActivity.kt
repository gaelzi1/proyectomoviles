// ProfileActivity.kt
package com.example.proyecto1

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var tvNombre: TextView
    private lateinit var tvApellidos: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvGenero: TextView
    private lateinit var tvEstado: TextView
    private lateinit var tvFechaNacimiento: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        tvNombre = findViewById(R.id.tvNombre)
        tvApellidos = findViewById(R.id.tvApellidos)
        tvEmail = findViewById(R.id.tvEmail)
        tvTelefono = findViewById(R.id.tvTelefono)
        tvGenero = findViewById(R.id.tvGenero)
        tvEstado = findViewById(R.id.tvEstado)
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento)

        val userId = auth.currentUser?.uid
        if (userId != null) {
            loadUserData(userId)
        } else {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadUserData(userId: String) {
        firestore.collection("usuarios").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    tvNombre.text = document.getString("nombre")
                    tvApellidos.text = document.getString("apellidos")
                    tvEmail.text = document.getString("email")
                    tvTelefono.text = document.getString("telefono")
                    tvGenero.text = document.getString("genero")
                    tvEstado.text = document.getString("estado")
                    tvFechaNacimiento.text = document.getString("fechaNacimiento")
                } else {
                    Toast.makeText(this, "No se encontraron datos del usuario", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show()
            }
    }
}
