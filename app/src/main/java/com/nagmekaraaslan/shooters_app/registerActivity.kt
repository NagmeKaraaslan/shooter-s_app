package com.nagmekaraaslan.shooters_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class registerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // Şifre fonksiyonu
    private fun isPasswordValid(password: String): Boolean {
        val hasUpper = password.any { it.isUpperCase() }
        val hasLower = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasPunctuation = password.any { !it.isLetterOrDigit() }
        Log.d("PASSWORD_CHECK", "Upper:$hasUpper Lower:$hasLower Digit:$hasDigit Punct:$hasPunctuation")
        return hasUpper && hasLower && hasDigit && hasPunctuation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        Toast.makeText(this, "Welcome to Register Page", Toast.LENGTH_SHORT).show()

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val rgUserType = findViewById<RadioGroup>(R.id.rgUserType)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val selectedTypeId = rgUserType.checkedRadioButtonId

            // 1. ADIM: Rol seçimi kontrolü (Hayalet veriyi engelleme)
            if (selectedTypeId == -1) {
                Toast.makeText(this, "Please select a user type!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. ADIM: Seçilen ID'ye göre KEY ataması
            val selectedRole = when (selectedTypeId) {
                R.id.rbModel -> "Model"
                R.id.rbPhotographer -> "Photographer"
                R.id.rbAgency -> "Agency"
                else -> {
                    // Güvenlik önlemi: Eğer beklenmedik bir durum olursa kaydı engelle
                    Toast.makeText(this, "Invalid role selection!", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // 3. ADIM: Boş alan ve şifre validasyon kontrolü
            if (email.isNotEmpty() && password.isNotEmpty()) {

                if (!isPasswordValid(password)) {
                    Toast.makeText(this, "Password must include uppercase, lowercase, digits and punctuation.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                // 4. ADIM: Firebase Authentication ile kayıt
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid

                            if (userId != null) {
                                // 5. ADIM: Realtime Database'e İngilizce keyler ile kayıt
                                val database = FirebaseDatabase.getInstance("https://shooter-s-7b8e2-default-rtdb.firebaseio.com/").reference

                                // "Users" -> "UID" -> "role" : "Model/Photographer/Agency"
                                database.child("Users").child(userId).child("role").setValue(selectedRole)
                                    .addOnSuccessListener {
                                        Log.d("REGISTER_SUCCESS", "Role saved: $selectedRole for UID: $userId")
                                        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

                                        // Giriş sayfasına yönlendir
                                        val intent = Intent(this@registerActivity, signInActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("REGISTER_ERROR", "DB Error: ${e.message}")
                                        Toast.makeText(this, "Database Error: ${e.message}", Toast.LENGTH_LONG).show()
                                    }
                            }
                        } else {
                            Toast.makeText(this, "Auth Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}