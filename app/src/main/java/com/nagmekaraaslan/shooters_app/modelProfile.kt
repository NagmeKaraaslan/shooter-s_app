package com.nagmekaraaslan.shooters_app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nagmekaraaslan.shooters_app.adapters.ProfilePagerAdapter

class modelProfile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model_profile)

        auth = FirebaseAuth.getInstance()

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val btnEdit = findViewById<ImageButton>(R.id.btnEdit)
        val tvName = findViewById<TextView>(R.id.tvName)
        val tvSurname = findViewById<TextView>(R.id.tvSurname)
        val tvEmail = findViewById<TextView>(R.id.tvEmail)
        val tvPhone = findViewById<TextView>(R.id.tvPhone)

        val adapter = ProfilePagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when(position) {
                0 -> "Detailed Infos"
                1 -> "Shoots"
                2 -> "Shoot Goals"
                else -> ""
            }
        }.attach()

        btnEdit.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }

        // Firebase'den verileri oku
        val userId = auth.currentUser?.uid ?: return
        val db = FirebaseDatabase.getInstance("https://shooter-s-default-rtdb.firebaseio.com/").reference

        db.child("Users").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value?.toString() ?: "—"
                val surname = snapshot.child("surname").value?.toString() ?: "—"
                val email = snapshot.child("email").value?.toString() ?: "—"
                val phone = snapshot.child("phone").value?.toString() ?: "—"

                tvName.text = "Name: $name"
                tvSurname.text = "Surname: $surname"
                tvEmail.text = "E-mail address: $email"
                tvPhone.text = "Phone num.: $phone"
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}