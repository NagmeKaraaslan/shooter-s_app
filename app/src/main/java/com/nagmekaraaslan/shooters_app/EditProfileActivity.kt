package com.nagmekaraaslan.shooters_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val databaseUrl = "https://shooter-s-default-rtdb.firebaseio.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return

        // EditText bileşenlerini tanımla
        val etName = findViewById<EditText>(R.id.etName)
        val etSurname = findViewById<EditText>(R.id.etSurname)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etAgency = findViewById<EditText>(R.id.etAgency)
        val etBasedIn = findViewById<EditText>(R.id.etBasedIn)
        val etAge = findViewById<EditText>(R.id.etAge)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etWaist = findViewById<EditText>(R.id.etWaist)
        val etChest = findViewById<EditText>(R.id.etChest)
        val etHips = findViewById<EditText>(R.id.etHips)
        val etLegLength = findViewById<EditText>(R.id.etLegLength)
        val etBiography = findViewById<EditText>(R.id.etBiography)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val dbRef = FirebaseDatabase.getInstance(databaseUrl).reference.child("Users").child(userId)

        // 1. ADIM: MEVCUT VERİLERİ GETİR VE KUTUCUKLARI DOLDUR
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    etName.setText(snapshot.child("name").value?.toString() ?: "")
                    etSurname.setText(snapshot.child("surname").value?.toString() ?: "")
                    etEmail.setText(snapshot.child("email").value?.toString() ?: "")
                    etPhone.setText(snapshot.child("phone").value?.toString() ?: "")
                    etAgency.setText(snapshot.child("agency").value?.toString() ?: "")
                    etBasedIn.setText(snapshot.child("basedIn").value?.toString() ?: "")
                    etAge.setText(snapshot.child("age").value?.toString() ?: "")
                    etHeight.setText(snapshot.child("height").value?.toString() ?: "")
                    etWeight.setText(snapshot.child("weight").value?.toString() ?: "")
                    etWaist.setText(snapshot.child("waist").value?.toString() ?: "")
                    etChest.setText(snapshot.child("chest").value?.toString() ?: "")
                    etHips.setText(snapshot.child("hips").value?.toString() ?: "")
                    etLegLength.setText(snapshot.child("legLength").value?.toString() ?: "")
                    etBiography.setText(snapshot.child("biography").value?.toString() ?: "")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfileActivity, "Veriler yüklenemedi: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })

        // 2. ADIM: GÜNCELLEMELERİ KAYDET
        btnSave.setOnClickListener {
            val updates = mapOf(
                "name" to etName.text.toString().trim(),
                "surname" to etSurname.text.toString().trim(),
                "email" to etEmail.text.toString().trim(),
                "phone" to etPhone.text.toString().trim(),
                "agency" to etAgency.text.toString().trim(),
                "basedIn" to etBasedIn.text.toString().trim(),
                "age" to etAge.text.toString().trim(),
                "height" to etHeight.text.toString().trim(),
                "weight" to etWeight.text.toString().trim(),
                "waist" to etWaist.text.toString().trim(),
                "chest" to etChest.text.toString().trim(),
                "hips" to etHips.text.toString().trim(),
                "legLength" to etLegLength.text.toString().trim(),
                "biography" to etBiography.text.toString().trim()
            )

            dbRef.updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profil başarıyla güncellendi!", Toast.LENGTH_SHORT).show()
                    finish() // Düzenleme bitince bir önceki sayfaya (Profile) döner
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Hata: ${it.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}