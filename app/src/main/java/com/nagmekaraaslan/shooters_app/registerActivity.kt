package com.nagmekaraaslan.shooters_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.content.Intent

class registerActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    //neden 'private lateinit var? : private ile değişkeni register sayfasına özel kıldık, lateinit ile kotlin'e değişkenin şu an dolu olmadığını bildiğimiz ama dolacağına emin olması gerektiğini söyledik

    fun isPasswordValid(password: String): Boolean {
        val hasUpper = password.any { it.isUpperCase() }
        val hasLower = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasPunctuation = password.any { !it.isLetterOrDigit() }
        android.util.Log.d("PASSWORD", "Upper:$hasUpper Lower:$hasLower Digit:$hasDigit Punct:$hasPunctuation"
        return hasUpper && hasLower && hasDigit && hasPunctuation
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        Toast.makeText(this, "Kayıt sayfasına hoş geldiniz.",Toast.LENGTH_SHORT).show()

        // Firebase Auth'ı başlatıyoruz - db ile bağlantı kuruyoruz - bilgileri göndermek için hazır hale getiriyoruz.
        auth = FirebaseAuth.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val rgUserType = findViewById<RadioGroup>(R.id.rgUserType)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Hangi RadioButton seçildiğini buluyoruz
            val selectedTypeId = rgUserType.checkedRadioButtonId
            val selectedType = findViewById<RadioButton>(selectedTypeId)?.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && selectedTypeId != -1) {
                // Firebase'e kullanıcıyı kaydet
                if (!isPasswordValid(password)) {
                    Toast.makeText(this, "Şifre büyük harf, küçük harf, rakam ve noktalama işareti içermelidir.", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task -> //task: sonuc paketimizin ismidir.
                        if (task.isSuccessful) {
                            // Kayıt başarılıysa, kullanıcı tipini veritabanına yaz
                            val userId = auth.currentUser?.uid
                            val database = FirebaseDatabase.getInstance("https://shooter-s-default-rtdb.firebaseio.com/").reference

                            // önce email ve şifre kaydetmemizin sonra rol atamamızın sebebi email ve şifre kaydedilirken bir sorun çıkması durumunda veritabanında hayaet veri tutmamak
                            // "Users" ağacı altında kullanıcının ID'sine rolünü kaydediyoruz
                            database.child("Users").child(userId!!).child("role").setValue(selectedType)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Kayıt Başarılı!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
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