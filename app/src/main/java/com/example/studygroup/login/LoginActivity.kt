package com.example.studygroup.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast

import com.example.studygroup.main.MainActivity

import com.example.studygroup.databinding.ActivityLoginBinding
import com.example.studygroup.models.*
import com.example.studygroup.network.RetrofitClient
import com.example.studygroup.utils.SubjectUserUtils

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private lateinit var Username:String
    private lateinit var Password:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val loginAPI = retrofit.create(RetrofitClient::class.java)
        val loginCall = loginAPI.login(LoginUser(username,password))
        loginCall.enqueue(object:Callback<Token>{
            override fun onFailure(call: Call<Token>, t: Throwable) {
                Toast.makeText(this@LoginActivity,"Could not login credentials wrong",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<Token>, response: retrofit2.Response<Token>) {
                val token = response.body()?.token
                val currentUser = AuthUser(Username,token)
                SubjectUserUtils.setUser(currentUser)
                setAllSubjects()
                LaunchMainActivity()

            }
        })

    }
    fun setAllSubjects(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
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
                SubjectUserUtils.setSubjects(Subjects)

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
                response: retrofit2.Response<ArrayList<Message>>
            ) {
                var m_response  = response.body()
                Toast.makeText(this@LoginActivity, m_response?.get(0)?.text,Toast.LENGTH_LONG).show()

            }
        })

    }


}

