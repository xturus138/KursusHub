package com.example.kursushub.ui.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kursushub.ui.onBoarding.PageOneFragment
import com.example.kursushub.ui.onBoarding.PageTwoFragment

class LandingPageAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = PageOneFragment()
            1 -> fragment = PageTwoFragment()
        }
        return fragment as Fragment
    }
}