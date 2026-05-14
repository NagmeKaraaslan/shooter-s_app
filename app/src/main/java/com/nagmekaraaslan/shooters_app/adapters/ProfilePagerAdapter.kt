package com.nagmekaraaslan.shooters_app.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nagmekaraaslan.shooters_app.fragments.DetailedInfosFragment
import com.nagmekaraaslan.shooters_app.fragments.ShootsFragment
import com.nagmekaraaslan.shooters_app.fragments.ShootGoalsFragment
import com.nagmekaraaslan.shooters_app.fragments.AgencyInfosFragment

class ProfilePagerAdapter(fragmentActivity: FragmentActivity, private val isAgency: Boolean = false) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = if (isAgency) 2 else 3

    override fun createFragment(position: Int): Fragment {
        return if (isAgency) {
            when(position) {
                0 -> AgencyInfosFragment()
                1 -> ShootsFragment() // Burayı ileride LatestShootsFragment ile güncelleyebiliriz
                else -> throw IllegalArgumentException("Invalid position")
            }
        } else {
            when(position) {
                0 -> DetailedInfosFragment()
                1 -> ShootsFragment()
                2 -> ShootGoalsFragment()
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }
}