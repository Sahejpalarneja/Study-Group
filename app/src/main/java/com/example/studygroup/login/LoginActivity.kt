package com.example.studygroup.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.example.studygroup.Handlers.UserDataHandler
import com.example.studygroup.main.MainActivity

import com.example.studygroup.databinding.ActivityLoginBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var Email:String
    private lateinit var Password:String
    private lateinit var User:FirebaseUser

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if(currentUser != null)
        {
            User = currentUser
            UserDataHandler.getUser(User.uid)
            //SubjectUserUtils.setUser(user)
            LaunchMainActivity()



        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin?.setOnClickListener {
            if(ValidateInput())
            {
                FirebaseLogIn(Email, Password)
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

        Email = binding.etEmail!!.text.toString().trim{it <=' '}
        Password= binding.etPassword!!.text.toString().trim{it <=' '}

        when {
            TextUtils.isEmpty(Email)-> {
                Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show()
                return false
            }
            TextUtils.isEmpty(Password) -> {
                Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show()
                return false
            }
            Email.isNotEmpty()  && Password.isNotEmpty()  -> {
                return true
            }

        }
        return false
    }

    fun FirebaseLogIn(Email: String, Password: String){
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener { task ->
            if (task.isSuccessful)
            {
                User = task.result!!.user!!
                Toast.makeText(this,"Login Successfull!",Toast.LENGTH_SHORT).show()
                 UserDataHandler.getUser(User.uid)
                //SubjectUserUtils.setUser(user)
                LaunchMainActivity()

            }
            else
            {
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
            }
        }
    }
}

