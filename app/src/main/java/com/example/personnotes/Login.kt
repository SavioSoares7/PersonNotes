package com.example.personnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.example.personnotes.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Login : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val binding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputEmail : EditText = binding.inputEmail
        val inputPassword : EditText = binding.inputPassword
        val btnToEnter : TextView = binding.btnEnter

        btnToEnter.setOnClickListener {
            userLogin(inputEmail, inputPassword)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun userLogin(inpEmail : EditText, inpPassword : EditText){
        val email = inpEmail.text.toString()
        val password = inpPassword.text.toString()

        if(email.isEmpty() and password.isEmpty()){
            inpEmail.setBackgroundResource(R.drawable.inp_error)
            inpPassword.setBackgroundResource(R.drawable.inp_error)
        }

        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Toast.makeText(this, "Conectado com sucesso!!!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Confira seu email ou senha!!!", Toast.LENGTH_LONG).show()
                }
            }
    }
}