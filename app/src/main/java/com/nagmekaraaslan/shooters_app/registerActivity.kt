package com.nagmekaraaslan.shooters_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent
import android.util.Log

class registerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    fun isPasswordValid(password: String): Boolean {
        val hasUpper = password.any { it.isUpperCase() }
        val hasLower = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasPunctuation = password.any { !it.isLetterOrDigit() }
        android.util.Log.d("PASSWORD", "Upper:$hasUpper Lower:$hasLower Digit:$hasDigit Punct:$hasPunctuation")
        return hasUpper && hasLower && hasDigit && hasPunctuation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Toast.makeText(this, "Kayıt sayfasına hoş geldiniz.", Toast.LENGTH_SHORT).show()

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val rgUserType = findViewById<RadioGroup>(R.id.rgUserType)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val selectedTypeId = rgUserType.checkedRadioButtonId
            val selectedType = findViewById<RadioButton>(selectedTypeId)?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && selectedTypeId != -1) {

                if (!isPasswordValid(password)) {
                    Toast.makeText(this, "Şifre büyük harf, küçük harf, rakam ve noktalama işareti içermelidir.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid

                            android.util.Log.d("REGISTER", "userId: $userId")
                            android.util.Log.d("REGISTER", "currentUser: ${auth.currentUser}")

                            if (userId != null) {
                                val database = FirebaseDatabase.getInstance("https://shooter-s-default-rtdb.firebaseio.com/").reference
                                database.child("Users").child(userId).child("role").setValue(selectedType)
                                    .addOnSuccessListener {
                                        Log.d("KAYIT", "Rol kaydedildi: $selectedType, UID: $userId")
                                        android.util.Log.d("REGISTER", "Role saved successfully")
                                        Toast.makeText(this, "Register Successful!", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this@registerActivity, signInActivity::class.java)
                                        intent.putExtra("USER_TYPE", selectedType)
                                        startActivity(intent)
                                        finish()
                                    }
                                    .addOnFailureListener {
                                        android.util.Log.d("REGISTER", "Role save failed: ${it.message}")
                                        Toast.makeText(this, "DB Error: ${it.message}", Toast.LENGTH_LONG).show()
                                    }
                            } else {
                                Toast.makeText(this, "Kullanıcı ID alınamadı.", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(this, "Hata: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun ve bir rol seçin.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}