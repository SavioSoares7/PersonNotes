package com.example.personnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personnotes.databinding.ActivitySingInBinding

class singIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivitySingInBinding = ActivitySingInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toRegister = binding.toRegister

        toRegister.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)
        }

    }
}