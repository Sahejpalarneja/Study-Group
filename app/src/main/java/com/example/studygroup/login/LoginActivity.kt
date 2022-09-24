package com.example.studygroup.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.studygroup.Handlers.SubjectDataHandler

import com.example.studygroup.main.MainActivity

import com.example.studygroup.databinding.ActivityLoginBinding
import com.example.studygroup.models.*
import com.example.studygroup.network.RetrofitClient
import com.example.studygroup.utils.SubjectUserUtils
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private lateinit var Username:String
    private lateinit var Password:String

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
        Stetho.initializeWithDefaults(this)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin?.setOnClickListener {
            if(ValidateInput())
            {
                login(Username,Password )
            }
        }

        binding.btnRegister?.setOnClickListener {
            val intent = Intent()
            intent.setClass(this,RegisterActivity::class.java)
            startActivity(intent)
        }
        var temp = SubjectDataHandler.getSubjects()[0]
        Toast.makeText(this,temp.name,Toast.LENGTH_LONG).show()
    }

    fun LaunchMainActivity()
    {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK



        startActivity(intent)
        finish()
    }

    fun ValidateInput():Boolean
    {

        Username = binding.etUsername!!.text.toString().trim{it <=' '}
        Password= binding.etPassword!!.text.toString().trim{it <=' '}

        when {
            TextUtils.isEmpty(Username)-> {
                Toast.makeText(this, "Please Enter Username", Toast.LENGTH_SHORT).show()
                return false
            }
            TextUtils.isEmpty(Password) -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                return false
            }
            Username.isNotEmpty()  && Password.isNotEmpty()  -> {
                return true
            }

        }
        return false
    }
    fun login(username:String,password:String){

        val loginCall = this.API.login(LoginUser(username,password))
        loginCall.enqueue(object:Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Could not login credentials wrong",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Token>, response: Response<Token>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token.toString()
                    setUser(Username,token)
                }
                else
                {
                    Toast.makeText(this@LoginActivity, "Credentials wrong",Toast.LENGTH_LONG).show()
                }

            }
        })

    }

    fun setUser(Username:String,token:String)
    {
        val header = "Token "+token
        val IdCall = this.API.getUserID(header,username = Username)
        IdCall.enqueue(object :Callback<Id>{
            override fun onFailure(call: Call<Id>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Id>, response: Response<Id>) {
                val Id = response.body()
                Toast.makeText(this@LoginActivity,Id?.id,Toast.LENGTH_LONG).show()
                val currentUser = AuthUser(Username,token,Id?.id)
                SubjectUserUtils.setUser(currentUser)
                LaunchMainActivity()


            }
        })
    }
    fun messages()
    {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val userAPI = retrofit.create(RetrofitClient::class.java)
        val messageCall = userAPI.get_messages()
        messageCall.enqueue(object:retrofit2.Callback<ArrayList<Message>>{
            override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ArrayList<Message>>,
                response: Response<ArrayList<Message>>
            ) {
                var m_response  = response.body()
                Toast.makeText(this@LoginActivity, m_response?.get(0)?.text,Toast.LENGTH_LONG).show()

            }
        })

    }


}

