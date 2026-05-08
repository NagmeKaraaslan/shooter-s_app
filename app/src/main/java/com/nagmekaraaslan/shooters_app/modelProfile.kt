package com.nagmekaraaslan.shooters_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class modelProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_model_profile)
        val kullaniciTipi = intent.getStringExtra("KULLANICI_TIPI")
        Toast.makeText(this, "Hoşgeldin, $kullaniciTipi!", Toast.LENGTH_LONG).show()
    }
}