package com.example.studygroup.splashscreen
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.WindowManager

import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.databinding.ActivitySplashScreenBinding
import com.example.studygroup.login.LoginActivity
import com.example.studygroup.models.Subject
import com.example.studygroup.network.RetrofitClient
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity(){
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onStart() {
        super.onStart()
        Stetho.initializeWithDefaults(this)
        setAllSubjects()
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
        },3000
        )

    }

    private fun setAllSubjects(){
        val client  =  OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val subjectAPI = retrofit.create(RetrofitClient::class.java)

        val subjectCall = subjectAPI.getSubjects()
        subjectCall.enqueue(object : Callback<ArrayList<Subject>> {
            override fun onFailure(call: Call<ArrayList<Subject>>, t: Throwable) {

                Log.e(ContentValues.TAG, "Failed to read value.",t.cause)
            }

            override fun onResponse(
                call: Call<ArrayList<Subject>>,
                response: Response<ArrayList<Subject>>
            ) {
                val Subjects = response.body()!!
                Log.i(ContentValues.TAG,"Subjects initialized")
                SubjectDataHandler.setSubjects(Subjects)

            }
        })
    }

}