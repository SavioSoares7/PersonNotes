package com.example.personnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personnotes.databinding.ActivitySplahScreenSingBinding

class SplahScreenSing : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivitySplahScreenSingBinding.inflate(layoutInflater)
        setContentView(binding.root )

        val btn_signIn = binding.btnSignIn
        val btn_register = binding.btnRegister

        btn_register.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

        btn_signIn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}