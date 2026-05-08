package com.nagmekaraaslan.shooters_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth

class signInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignIn = findViewById<Button>(R.id.btnSignIn)

        btnSignIn.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    android.util.Log.d("SIGNIN", "isSuccessful: ${task.isSuccessful}")
                    android.util.Log.d("SIGNIN", "exception: ${task.exception?.message}")
                    Toast.makeText(this, "Sonuç: ${task.isSuccessful} - ${task.exception?.message}", Toast.LENGTH_LONG).show()

                    if (task.isSuccessful) { //firebase sunucularına giden (Az önce girilmiş olan) eposta ve şifre doğru mu?

                        val user = task.result?.user //Kullanıcı verisi paketlenip bize ulaştı mı?
                        val userId = user?.uid //Bu kullanıcının kimlik numarası okunabiliyor mu? ki usertype ı ayırabilelim

                        if (userId != null) {
                            val database = FirebaseDatabase.getInstance("https://shooter-s-default-rtdb.firebaseio.com/").reference

                            // Kullanıcının rolünü veritabanından oku
                            database.child("Users").child(userId).child("role").get()
                                .addOnSuccessListener { snapshot ->
                                    val role = snapshot.value?.toString()
                                    val intent = when (role) {
                                        "Model" -> Intent(this, modelProfile::class.java)
                                        "Photographer" -> Intent(this, photographerProfile::class.java)
                                        "Agency" -> Intent(this, agencyProfile::class.java)
                                        else -> Intent(this, MainActivity::class.java)
                                    }
                                    intent.putExtra("USER_TYPE", role)
                                    startActivity(intent)
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed to read user role: ${it.message}", Toast.LENGTH_LONG).show()
                                }
                        } else {
                            // userId'nin alınamadığı durum (isSuccessful true olsa bile güvenlik için)
                            Toast.makeText(this, "User ID could not be retrieved.", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        // Giriş başarısız olduğunda (Şifre yanlış vb.) gösterilecek hata
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}