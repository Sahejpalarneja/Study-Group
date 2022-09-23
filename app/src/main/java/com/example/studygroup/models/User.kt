package com.example.studygroup.models


data class User(val username:String,val email:String,val password1: String,val password2 : String)
data class AuthUser(val username: String,val token:String?)
data class LoginUser(val username: String,val password: String)