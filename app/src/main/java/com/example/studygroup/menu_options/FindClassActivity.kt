package com.example.studygroup.menu_options

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.AddSubjectDialog
import com.example.studygroup.utils.SubjectUserUtils
import com.example.studygroup.R
import com.example.studygroup.adapters.SubjectAdapter
import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.models.Subject
import com.example.studygroup.Handlers.UserDataHandler
import com.example.studygroup.databinding.ActivityFindClassBinding

import com.example.studygroup.network.RetrofitClient
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class FindClassActivity : AppCompatActivity(),AddSubjectDialog.SubjectHandler {

    private lateinit var binding:ActivityFindClassBinding

    private lateinit var  subjectsRV:RecyclerView
    private lateinit var adapter:SubjectAdapter
    private lateinit var subjects:ArrayList<Subject>

    val client = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://study-group-2.herokuapp.com/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val API = retrofit.create(RetrofitClient::class.java)



    companion object{
        private  var enrolledClasses = SubjectUserUtils.getSubjects()
        private  var userID  = SubjectUserUtils.getUser().id
        private lateinit var context:Context

        fun setContext(context : Context){
            this.context = context
        }
        fun getContext():Context{
            return this.context
        }
        fun checkIfEnrolled(neptun:String): Boolean
        {
            for(i in enrolledClasses) {
                if(SubjectUserUtils.checkDuplicate(neptun))
                {
                    continue
                }
                if (i.equals(neptun))
                    return true
            }
            return false
        }
        fun addClass(neptun: String) :Context
        {
            if(UserDataHandler.joinClass(userID,neptun, enrolledClasses))
            {
                return context
            }
            else {
                //TODO add error message
                return context
            }
        }

        fun alreadyEnrolled():Context
        {
            return context
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindClassBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subjectsRV = binding.RVSubjects
        buildRecyclerView()
        FindClassActivity.Companion.setContext(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu?.findItem(R.id.search)


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

    fun showAddSubjectDialog()
    {
        AddSubjectDialog().show(supportFragmentManager,"Dialog")
    }
    fun joinSubject(){


    }


}