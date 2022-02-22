 package com.example.studygroup.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.studygroup.R
import com.example.studygroup.databinding.ActivityRegisterBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

 class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
     private lateinit var mAuth: FirebaseAuth
     private lateinit var mUser: FirebaseUser
     private lateinit var FirstName:String
     private lateinit var LastName:String
     private lateinit var Email:String
     private lateinit var Password:String
     private lateinit var RepeatPassword:String

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirstName= binding.etFirstName.text.toString().trim{it <=' '}
         LastName = binding.etLastName.text.toString().trim{it <=' '}
         Email = binding.etEmail.text.toString().trim{it <=' '}
         Password= binding.etPassword.text.toString().trim{it <=' '}
         RepeatPassword = binding.etRepeatpassword.text.toString().trim{it <=' '}


        mAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {
            Toast.makeText(this,"Chala",Toast.LENGTH_LONG).show()
            if (ValidateInput())
            {
                if(ValidateCredentials())
                {
                    FirebaseSignUp(Email,Password)
                }
            }

        }
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
         when {
             TextUtils.isEmpty(Email) -> {
                 Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show()
                 return false
             }
             TextUtils.isEmpty(FirstName) -> {
                 Toast.makeText(this, "Please Enter First Name", Toast.LENGTH_SHORT).show()
                 return false
             }
             TextUtils.isEmpty(LastName) -> {
                 Toast.makeText(this, "Please Enter Last Name", Toast.LENGTH_SHORT).show()
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
             Email.isNotEmpty() && FirstName.isNotEmpty() && LastName.isNotEmpty() && Password.isNotEmpty() && RepeatPassword.isNotEmpty() -> {
                 return true
             }

         }
         return false
     }
     fun FirebaseSignUp(Email: String, Password: String)
     {
         mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { task ->
             if (task.isSuccessful)
             {
                 Toast.makeText(this,"Registration Successfull!",Toast.LENGTH_SHORT).show()
             }
             else
             {
                 Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()
             }
         }
     }

}