package com.example.personnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personnotes.databinding.ActivityRegisterBinding

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding : ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var toEnterLogin = binding.toEnter

        toEnterLogin.setOnClickListener {
            val intent = Intent(this, singIn::class.java)
            startActivity(intent)
        }

    }
}