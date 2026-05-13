package com.nagmekaraaslan.shooters_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nagmekaraaslan.shooters_app.R

class DetailedInfosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detailed_infos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseDatabase.getInstance("https://shooter-s-default-rtdb.firebaseio.com/").reference

        db.child("Users").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                view.findViewById<TextView>(R.id.tvAjans).text = "Agency:   ${snapshot.child("agency").value ?: "—"}"
                view.findViewById<TextView>(R.id.tvBasedIn).text = "Based In:   ${snapshot.child("basedIn").value ?: "—"}"
                view.findViewById<TextView>(R.id.tvYas).text = "Age:   ${snapshot.child("age").value ?: "—"}"
                view.findViewById<TextView>(R.id.tvBoy).text = "Height:   ${snapshot.child("height").value ?: "—"} cm"
                view.findViewById<TextView>(R.id.tvKilo).text = "Weight:   ${snapshot.child("weight").value ?: "—"} kg"
                view.findViewById<TextView>(R.id.tvBel).text = "Waist:   ${snapshot.child("waist").value ?: "—"} cm"
                view.findViewById<TextView>(R.id.tvGogus).text = "Chest:   ${snapshot.child("chest").value ?: "—"} cm"
                view.findViewById<TextView>(R.id.tvKalca).text = "Hips:   ${snapshot.child("hips").value ?: "—"} cm"
                view.findViewById<TextView>(R.id.tvBacakBoyu).text = "Leg Length:   ${snapshot.child("legLength").value ?: "—"} cm"
                view.findViewById<TextView>(R.id.tvBiyografi).text = "${snapshot.child("biography").value ?: "—"}"
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}