package com.example.studygroup.ButtonActivities

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText

import androidx.fragment.app.DialogFragment
import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.databinding.AddSubjectDialogBinding
import com.example.studygroup.main.MainActivity
import com.example.studygroup.menu_options.FindClassActivity
import com.example.studygroup.models.Subject
import com.example.studygroup.network.RetrofitClient
import com.example.studygroup.utils.SubjectUserUtils
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient

import retrofit2.Call
import retrofit2.Callback
import com.example.studygroup.models.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AddSubjectDialog:DialogFragment() {

    val client  =  OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()
    val retrofit = Retrofit.Builder()
        .baseUrl("https://study-group-2.herokuapp.com/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val API = retrofit.create(RetrofitClient::class.java)

    interface SubjectHandler{


    }
    private lateinit var  subjectHandler :SubjectHandler

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if(context is SubjectHandler)
        {
            subjectHandler = context
        }
        else
        {
            throw RuntimeException("The Activity is not implementing the handler")
        }

    }

    private lateinit var etSubjectName:EditText
    private lateinit var etSubjectCode:EditText
    private lateinit var etProfessorName : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Add Subject")
        val dialogBinding = AddSubjectDialogBinding.inflate(layoutInflater)

        etSubjectName = dialogBinding.etSubjectName
        etSubjectCode = dialogBinding.etSubjectCode
        etProfessorName = dialogBinding.etProfessorName

        dialogBuilder.setView(dialogBinding.root)
        dialogBuilder.setPositiveButton("Ok"){_,_ ->}
        dialogBuilder.setNegativeButton("Cancel"){_,_->}
        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if(etSubjectName.text.isNotEmpty())
            {
                if(etSubjectCode.text.isNotEmpty())
                {
                    if(!SubjectDataHandler.checkDuplicate(etSubjectCode.text.toString())) {
                        val newSubject = Subject(
                            etSubjectName.text.toString(),
                            etSubjectCode.text.toString(),
                            etProfessorName.text.toString()
                        )
                        addSubjectToDB(newSubject)
                        SubjectDataHandler.addSubject(newSubject)
                        FindClassActivity.joinSubject(newSubject.neptun)
                        val mainIntent = Intent()
                        mainIntent.setClass(
                            FindClassActivity.getContext(),
                            MainActivity::class.java
                        )
                        startActivity(mainIntent)
                        dialog!!.dismiss()
                    }
                    else{
                        Log.i(TAG,"Already Exists")
                    }

                }
                else{
                    etSubjectCode.error = "This cannot be empty"

                }
            }
            else{
                etSubjectName.error = "This cannot be empty"
            }
        }
    }

    private fun addSubjectToDB(newSubject: Subject){
        val header  = "Token "+SubjectUserUtils.getUser().token
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name",newSubject.name)
            .addFormDataPart("neptun",newSubject.neptun)
            .addFormDataPart("professor",newSubject.professor)
            .build()
        val addSubjectPost = this.API.postAddSubject(header,body)
        addSubjectPost.enqueue(object :Callback<Response>{
            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.i(TAG,"Fail")
            }

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                Log.i(TAG,response.body()!!.message)
            }
        })

    }
}