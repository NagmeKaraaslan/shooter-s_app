package com.nagmekaraaslan.shooters_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        auth = FirebaseAuth.getInstance()
        
        // --- OTOMATİK GİRİŞ KONTROLÜ ---
        val currentUser = auth.currentUser
        if (currentUser != null) {
            checkUserRoleAndRedirect(currentUser.uid)
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button = findViewById<Button>(R.id.button)
        val buttonS = findViewById<Button>(R.id.signBtn)

        button.setOnClickListener {
            val goToRegister = Intent(this, registerActivity::class.java)
            startActivity(goToRegister)
        }

        buttonS.setOnClickListener {
            val goToSignIn = Intent(this, signInActivity::class.java)
            startActivity(goToSignIn)
        }
    }

    private fun checkUserRoleAndRedirect(userId: String) {
        val database = FirebaseDatabase.getInstance("https://shooter-s-7b8e2-default-rtdb.firebaseio.com/").reference
        
        database.child("Users").child(userId).child("role").get()
            .addOnSuccessListener { snapshot ->
                val role = snapshot.value?.toString()
                if (role != null) {
                    val intent = when (role) {
                        "Model" -> Intent(this, modelProfile::class.java)
                        "Photographer" -> Intent(this, photographerProfile::class.java)
                        "Agency" -> Intent(this, agencyProfile::class.java)
                        else -> null
                    }
                    
                    if (intent != null) {
                        intent.putExtra("USER_TYPE", role)
                        startActivity(intent)
                        finish()
                        return@addOnSuccessListener
                    }
                }
                // Rol bulunamadıysa (veritabanı sıfırlandığı için), oturumu kapatıp burada kalabiliriz
                // veya kullanıcıyı tekrar kayıt olmaya zorlayabiliriz.
                Toast.makeText(this, "Kullanıcı verisi bulunamadı, lütfen tekrar giriş yapın.", Toast.LENGTH_LONG).show()
                auth.signOut()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Hata oluştu: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}