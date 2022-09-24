package com.example.studygroup.network

import com.example.studygroup.models.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.*
import kotlin.collections.ArrayList

public interface RetrofitClient {

        @GET("messages/mobileid")
        fun get_messages():Call<ArrayList<Message>>

        @POST("login")
        fun login(@Body login_user : LoginUser):Call<Token>

        @GET("subjects")
        fun getSubjects():Call<ArrayList<Subject>>

}