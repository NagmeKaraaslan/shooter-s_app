package com.nagmekaraaslan.shooters_app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nagmekaraaslan.shooters_app.MainActivity
import com.nagmekaraaslan.shooters_app.R

class AgencyInfosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_agency_infos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = FirebaseAuth.getInstance()
        val userId = auth.currentUser?.uid ?: return
        val db = FirebaseDatabase.getInstance("https://shooter-s-7b8e2-default-rtdb.firebaseio.com/").reference

        val fabLogout = view.findViewById<FloatingActionButton>(R.id.fabLogout)
        fabLogout?.setOnClickListener {
            auth.signOut()
            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        db.child("Users").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) return
                
                view.findViewById<TextView>(R.id.tvAgencyCeo).text = snapshot.child("agencyCeo").value?.toString() ?: "—"
                view.findViewById<TextView>(R.id.tvBasedIn).text = snapshot.child("basedIn").value?.toString() ?: "—"
                view.findViewById<TextView>(R.id.tvAgeRange).text = snapshot.child("ageRange").value?.toString() ?: "—"
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}