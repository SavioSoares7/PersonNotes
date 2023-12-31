package com.example.personnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.personnotes.databinding.ActivityRegisterBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class register : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    //Initialize Firebase auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        var binding : ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val input_email = binding.inputEmail
        val input_password = binding.inputPassword
        val btn_register = binding.btnCreate
        
        val toEnterLogin = binding.toEnter

        toEnterLogin.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        btn_register.setOnClickListener {
            createAcount(input_email,input_password)
        }
    }

    private fun createAcount(inpEmail: EditText, inpPassword : EditText) {
        val email = inpEmail.text.toString().trim()
        val password = inpPassword.text.toString().trim()

        if (email.isEmpty() and password.isEmpty()) {
            inpEmail.setBackgroundResource(R.drawable.inp_error)
            inpPassword.setBackgroundResource(R.drawable.inp_error)

            Toast.makeText(this, "Informe todos os campos", Toast.LENGTH_LONG).show()

            return
        }

        if (password.length <= 3) {
            Toast.makeText(this, "Informe senha com mais de 3 caracteres", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    finish() // Opcional: Fechar a atividade de registro atual após o registro bem-sucedido
                } else {
                    Toast.makeText(this, "Cadastro não realizado!!!", Toast.LENGTH_LONG).show()
                }
            }
    }
}