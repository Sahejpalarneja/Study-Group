package com.example.studygroup.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.databinding.ActivitySplashScreenBinding
import com.example.studygroup.login.LoginActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onStart() {
        super.onStart()
        SubjectDataHandler.InitializeSubjects()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN

        )

        Handler().postDelayed({
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
            finish()
        },1000
        )
    }
}