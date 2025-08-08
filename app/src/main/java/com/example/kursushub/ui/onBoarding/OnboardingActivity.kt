package com.example.kursushub.ui.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kursushub.databinding.ActivityOnboardingBinding
import com.example.kursushub.ui.adapter.LandingPageAdapter
import com.example.kursushub.ui.auth.login.LoginActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
    }

    private fun setupViewPager() {
        val adapter = LandingPageAdapter(this)
        binding.viewPager.adapter = adapter
    }
}