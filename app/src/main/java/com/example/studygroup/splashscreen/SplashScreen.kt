package com.example.studygroup.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.databinding.ActivitySplashScreenBinding
import com.example.studygroup.ui.login.LoginActivity


/**
 * This Activity is to display a SplashScreen that would be displayed on the startup
 * of the app.
 */
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onStart() {
        super.onStart()
         // Here we Call the SubjectDataHandler class to initiate the subject list
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