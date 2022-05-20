 package com.example.studygroup.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.studygroup.main.MainActivity
import com.example.studygroup.databinding.ActivityRegisterBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.studygroup.data.User

import com.example.studygroup.Handlers.UserDataHandler


 class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
     private lateinit var mAuth: FirebaseAuth
     private lateinit var FirstName:String
     private lateinit var LastName:String
     private lateinit var Email:String
     private lateinit var Password:String
     private lateinit var RepeatPassword:String
     private lateinit var User :FirebaseUser

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener {

            if (ValidateInput())
            {
                if(ValidateCredentials())
                {

                    FirebaseSignUp(Email,Password)

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
         FirstName= binding.etFirstName.text.toString().trim{it <=' '}
         LastName = binding.etLastName.text.toString().trim{it <=' '}
         Email = binding.etEmail.text.toString().trim{it <=' '}
         Password= binding.etPassword.text.toString().trim{it <=' '}
         RepeatPassword = binding.etRepeatPassword.text.toString().trim{it <=' '}
         when {
             TextUtils.isEmpty(Email)-> {
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
     fun FirebaseSignUp(Email: String, Password: String){
         mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener { task ->
             if (task.isSuccessful)
             {
                 this.User = task.result!!.user!!
                 var user  = User(User.uid,FirstName+" "+LastName, ArrayList())
                 UserDataHandler.writeUser(user)

                 Toast.makeText(this,"Registration Successfull!",Toast.LENGTH_SHORT).show()
                 UserDataHandler.getUser(User.uid)
                 LaunchMainActivity()

             }
             else
             {

                 Toast.makeText(this,task.exception?.message,Toast.LENGTH_LONG).show()

             }
         }
     }

}