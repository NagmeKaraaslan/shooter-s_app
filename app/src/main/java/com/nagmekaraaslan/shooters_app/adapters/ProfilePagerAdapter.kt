package com.nagmekaraaslan.shooters_app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nagmekaraaslan.shooters_app.fragments.DetailedInfosFragment
import com.nagmekaraaslan.shooters_app.fragments.ShootsFragment
import com.nagmekaraaslan.shooters_app.fragments.ShootGoalsFragment

class ProfilePagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> DetailedInfosFragment()
            1 -> ShootsFragment()
            2 -> ShootGoalsFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}