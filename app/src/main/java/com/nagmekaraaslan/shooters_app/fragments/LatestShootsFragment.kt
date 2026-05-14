package com.nagmekaraaslan.shooters_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nagmekaraaslan.shooters_app.R
import com.nagmekaraaslan.shooters_app.adapters.LatestShootsAdapter

class LatestShootsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_latest_shoots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerLatestShoots)
        recyclerView.layoutManager = LinearLayoutManager(context)
        
        // Örnek veri listesi (İleride Firebase'den çekilecek)
        val dummyList = listOf("Shoot 1", "Shoot 2", "Shoot 3")
        recyclerView.adapter = LatestShootsAdapter(dummyList)
    }
}