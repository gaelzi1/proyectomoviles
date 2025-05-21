package com.example.proyecto1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var genreEditText: EditText
    private lateinit var stateEditText: EditText
    private lateinit var birthdateEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var lastNameEditText: EditText


    private lateinit var registerButton: Button
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Inputs
        emailEditText = findViewById(R.id.etEmail)
        passwordEditText = findViewById(R.id.etPassword)
        confirmPasswordEditText = findViewById(R.id.etConfirmPassword)
        phoneEditText = findViewById(R.id.etPhoneNumber)
        genreEditText = findViewById(R.id.etGenre)
        stateEditText = findViewById(R.id.etState)
        birthdateEditText = findViewById(R.id.etBirthdate)
        nameEditText = findViewById(R.id.etName)
        lastNameEditText = findViewById(R.id.etLastName)


        registerButton = findViewById(R.id.btnRegister)
        loginButton = findViewById(R.id.btnLogin)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    registerUser(email, password)
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // ¡Aquí ya estás seguro que el usuario está creado!
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        saveAdditionalData(uid)
                    } else {
                        Toast.makeText(this, "Error: UID nulo", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun saveAdditionalData(userId: String) {
        val email = emailEditText.text.toString()
        val telefono = phoneEditText.text.toString()
        val genero = genreEditText.text.toString()
        val estado = stateEditText.text.toString()
        val fechaNacimiento = birthdateEditText.text.toString()
        val nombre = nameEditText.text.toString()
        val apellidos = lastNameEditText.text.toString()

        val userData = hashMapOf(
            "uid" to userId,
            "email" to email,
            "nombre" to nombre,
            "apellidos" to apellidos,
            "telefono" to telefono,
            "genero" to genero,
            "estado" to estado,
            "fechaNacimiento" to fechaNacimiento
        )

        firestore.collection("usuarios").document(userId)
            .set(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))

                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar datos en Firestore", Toast.LENGTH_SHORT).show()
            }
    }

}
