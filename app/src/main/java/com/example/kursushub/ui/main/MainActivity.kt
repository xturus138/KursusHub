// app/src/main/java/com/example/kursushub/ui/main/MainActivity.kt

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
            // Untuk sementara, kita asumsikan jika UID ada, user sudah login
            // Logika isFirstTimeUser dapat Anda kembangkan lebih lanjut jika diperlukan
            if (isLoggedIn) {
                // Pengguna sudah login, arahkan ke HomeActivity
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                // Pengguna belum login, cek apakah ini pertama kali
                // Anda bisa menambahkan logika pengecekan onboarding di sini jika perlu
                val isFirstTimeUser = true // Asumsi untuk sekarang
                if (isFirstTimeUser) {
                    val intent = Intent(this, OnboardingActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}