 package com.example.studygroup.login

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.studygroup.main.MainActivity
import com.example.studygroup.databinding.ActivityRegisterBinding
import com.example.studygroup.models.AuthUser
import com.example.studygroup.models.User
import com.example.studygroup.network.RetrofitClient
import com.example.studygroup.utils.SubjectUserUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.MultipartBody

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



 class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

     private lateinit var Username:String
     private lateinit var Email:String
     private lateinit var Password:String
     private lateinit var RepeatPassword:String


     val client  =  OkHttpClient.Builder()
         .addNetworkInterceptor(StethoInterceptor())
         .build()
     val retrofit = Retrofit.Builder()
         .baseUrl("https://study-group-2.herokuapp.com/api/")
         .client(client)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
     val API = retrofit.create(RetrofitClient::class.java)


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {

            if (ValidateInput())
            {
                if(ValidateCredentials())
                {
                    val user = User(Username,Email,Password,RepeatPassword)
                    register(user)


                }
            }

        }
         binding.btnLogIn.setOnClickListener{
             val intent = Intent()
             intent.setClass(this,LoginActivity::class.java)
             startActivity(intent)
         }



    }

     fun LaunchMainActivity()
     {
         val intent = Intent(this,MainActivity::class.java)
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

         startActivity(intent)
         finish()
     }
     fun ValidateCredentials():Boolean
     {
         if(Patterns.EMAIL_ADDRESS.matcher(Email).matches())
         {
             if(Password.length>=5)
             {
                 if(Password == RepeatPassword)
                 {
                     return true
                 }
                 else
                 {
                     Toast.makeText(this,"The Passwords don't match",Toast.LENGTH_SHORT).show()
                     return false
                 }

             }
             else
             {
                 Toast.makeText(this,"Password is too short",Toast.LENGTH_SHORT).show()
                 return false
             }

         }
         else
         {
             Toast.makeText(this,"Enter a valid Email Address",Toast.LENGTH_SHORT).show()
             return false
         }
     }
     fun ValidateInput():Boolean
     {
         Username= binding.etUsername.text.toString().trim{it <=' '}
         Email = binding.etEmail.text.toString().trim{it <=' '}
         Password= binding.etPassword.text.toString().trim{it <=' '}
         RepeatPassword = binding.etRepeatPassword.text.toString().trim{it <=' '}
         when {
             TextUtils.isEmpty(Email)-> {
                 Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show()
                 return false
             }
             TextUtils.isEmpty(Username) -> {
                 Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show()
                 return false
             }
             TextUtils.isEmpty(Password) -> {
                 Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                 return false
             }
             TextUtils.isEmpty(RepeatPassword) -> {
                 Toast.makeText(this, "Please Enter Password Again", Toast.LENGTH_SHORT).show()
                 return false
             }
             Email.isNotEmpty() && Username.isNotEmpty() && Password.isNotEmpty() && RepeatPassword.isNotEmpty() -> {
                 return true
             }

         }
         return false
     }


     fun register(user:User){
         val body=MultipartBody.Builder()
             .setType(MultipartBody.FORM)
             .addFormDataPart("username",user.username)
             .addFormDataPart("email",user.email)
             .addFormDataPart("password",user.password1)
             .addFormDataPart("password2",user.password2)
             .build()
         val registerPost = this.API.register(body)
         registerPost.enqueue(object: Callback<AuthUser> {
             override fun onFailure(call: Call<AuthUser>, t: Throwable) {
                 Log.i(TAG,"Registration failed")
             }

             override fun onResponse(call: Call<AuthUser>, response: Response<AuthUser>) {
                 val newUser = response.body()
                 Log.i(TAG,newUser!!.username+" Registered")
                 Thread.sleep(2000)
                 SubjectUserUtils.setUser(newUser)
                 LaunchMainActivity()
             }

         })

     }

}