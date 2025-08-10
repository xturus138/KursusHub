package com.example.kursushub.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import com.example.kursushub.R
import com.example.kursushub.ui.ViewModelFactory
import com.example.kursushub.ui.auth.login.LoginActivity
import com.example.kursushub.ui.onBoarding.OnboardingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        viewModel.getUserSession().observe(this) { (uid, isLoggedIn) ->
            if (isLoggedIn) {
                startActivity(Intent(this, HomeActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            } else {
                val isFirstTimeUser = true
                if (isFirstTimeUser) {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                    finish()
                }
            }
        }

    }
}