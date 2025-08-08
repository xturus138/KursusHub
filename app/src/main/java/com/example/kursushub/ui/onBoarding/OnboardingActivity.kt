package com.example.kursushub.ui.onBoarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kursushub.databinding.ActivityOnboardingBinding
import com.example.kursushub.ui.adapter.LandingPageAdapter

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}