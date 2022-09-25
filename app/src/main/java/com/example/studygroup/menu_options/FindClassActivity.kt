package com.example.studygroup.menu_options

import android.annotation.SuppressLint
import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.AddSubjectDialog
import com.example.studygroup.utils.SubjectUserUtils
import com.example.studygroup.R
import com.example.studygroup.adapters.SubjectAdapter
import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.models.Subject
import com.example.studygroup.databinding.ActivityFindClassBinding
import com.example.studygroup.models.Response

import com.example.studygroup.network.RetrofitClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindClassActivity : AppCompatActivity(),AddSubjectDialog.SubjectHandler {

    private lateinit var binding:ActivityFindClassBinding

    private lateinit var  subjectsRV:RecyclerView
    private lateinit var adapter:SubjectAdapter
    private lateinit var subjects:ArrayList<Subject>

    companion object {
        private var userID = SubjectUserUtils.getUser().id
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://study-group-2.herokuapp.com/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val API = retrofit.create(RetrofitClient::class.java)
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun setContext(context: Context) {
            this.context = context
        }

        fun getContext(): Context {
            return this.context
        }


        fun joinSubject(neptun: String) {
            val header = "Token "+SubjectUserUtils.getUser().token
            val joinPOST = this.API.postJoinClass(header, neptun, userID!!.toInt())
            joinPOST.enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    Toast.makeText(context, "Error in joining class", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            context,
                            response.body()?.message.toString(),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(context, "Error in joining class", Toast.LENGTH_LONG).show()
                    }
                }
            })
            val joinedSubject = SubjectDataHandler.getClassFromCode(neptun)
            SubjectUserUtils.addUserSubject(joinedSubject)
        }

        fun alreadyEnrolled(): Context {
            return context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subjectsRV = binding.RVSubjects
        buildRecyclerView()
        setContext(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_class)
        {
            showAddSubjectDialog()
        }
        return true
    }

    private fun buildRecyclerView(){
        subjects = SubjectDataHandler.getSubjects()
        adapter = SubjectAdapter(this,subjects)

        val manager = LinearLayoutManager(this)
        subjectsRV.layoutManager = manager
        subjectsRV.adapter = adapter

    }

    fun showAddSubjectDialog() {
        AddSubjectDialog().show(supportFragmentManager,"Dialog")
    }


}
