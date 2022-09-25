package com.example.studygroup.network

import com.example.studygroup.models.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback

import retrofit2.http.*

import kotlin.collections.ArrayList

interface RetrofitClient {

        @POST("register")
        fun register(@Body body: RequestBody):Call<AuthUser>

        @POST("login")
        fun login(@Body login_user: LoginUser): Call<Token>

        @GET("subjects")
        fun getSubjects(): Call<ArrayList<Subject>>

        @GET("get_id")
        fun getUserID(
                @Header("Authorization") token: String,
                @Query("username") username: String
        ): Call<Id>

        @GET("get_user_subjects")
        fun getUserSubjects(
                @Header("Authorization") token: String,
                @Query("id") id: Int
        ): Call<ArrayList<Subject>>

        @POST("join_class")
        fun postJoinClass(
                @Header("Authorization") token: String,
                @Query("neptun") neptun: String,
                @Query("id") id: Int
        ): Call<Response>

        @POST("add_subject")
        fun postAddSubject(@Header("Authorization")token:String,@Body body:RequestBody):Call<Response>

        @GET("get_messages")
        fun getSubjectMessages(@Header("Authorization")token: String,@Query("neptun")neptun: String):Call<ArrayList<Message>>

        @POST("send_message")
        fun postSendMessage(@Header("Authorization")token: String,@Body body: RequestBody):Call<Response>
}
