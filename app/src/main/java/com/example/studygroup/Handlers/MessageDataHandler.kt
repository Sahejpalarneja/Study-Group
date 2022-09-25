package com.example.studygroup.Handlers

import android.content.ContentValues.TAG
import android.util.Log
import com.example.studygroup.models.Message
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



class MessageDataHandler {
    companion object{
        var messages = ArrayList<Message>()
        val client  =  OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val API = retrofit.create(RetrofitClient::class.java)

        fun loadMessages(neptun:String) {
            val header = "Token "+SubjectUserUtils.getUser().token
            val messageCall = this.API.getSubjectMessages(header,neptun)
            messageCall.enqueue(object :Callback<ArrayList<Message>>{
                override fun onFailure(call: Call<ArrayList<Message>>, t: Throwable) {
                    Log.i(TAG,"Failed to initialize texts")
                }

                override fun onResponse(
                    call: Call<ArrayList<Message>>,
                    response: Response<ArrayList<Message>>
                ) {
                    messages = response.body()!!
                    Log.i(TAG,"Mesages initialized")
                }
            })
        }

        fun postMessage(sender:String,text:String,N_code:String){
            val header = "Token "+SubjectUserUtils.getUser().token
            val body = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("sender",sender)
                .addFormDataPart("text",text)
                .addFormDataPart("N_code",N_code)
                .build()
            val messagePost = this.API.postSendMessage(header,body)
            messagePost.enqueue(object :Callback<com.example.studygroup.models.Response>{
                override fun onFailure(call: Call<com.example.studygroup.models.Response>, t: Throwable) {
                    Log.i(TAG,"Failed to send message")

                }

                override fun onResponse(call: Call<com.example.studygroup.models.Response>, response: Response<com.example.studygroup.models.Response>) {
                    Log.i(TAG,response.body()!!.message)
                }
            })
        }

    }
}