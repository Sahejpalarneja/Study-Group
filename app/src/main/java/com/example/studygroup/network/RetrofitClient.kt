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

        @Headers("Authorization: Token 9ae0eb8a602eb76a47c8c5b553b20ee03adb9631")
        @GET("subjects")
        fun getSubjects():Call<ArrayList<Subject>>

}