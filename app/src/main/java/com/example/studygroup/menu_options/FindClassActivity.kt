package com.example.studygroup.menu_options

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.AddSubjectDialog
import com.example.studygroup.ButtonActivities.SubjectUserUtils
import com.example.studygroup.R
import com.example.studygroup.adapters.SubjectAdapter
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.data.Subject
import com.example.studygroup.data.UserDataHandler
import com.example.studygroup.databinding.ActivityFindClassBinding


class FindClassActivity : AppCompatActivity(),AddSubjectDialog.SubjectHandler {

    private lateinit var binding:ActivityFindClassBinding
    private lateinit var  subjectsRV:RecyclerView
    private lateinit var adapter:SubjectAdapter
    private lateinit var subjects:ArrayList<Subject>
    private lateinit var enrolledClasses : ArrayList<String>
    private lateinit var userID : String


    companion object{
        private  var enrolledClasses = SubjectUserUtils.getUser().Classes
        private  var userID  = SubjectUserUtils.getUser().UserID
        private lateinit var context:Context

        fun setContext(context : Context){
            this.context = context
        }
        fun getContext():Context{
            return this.context
        }
        fun checkIfEnrolled(NEPTUN:String): Boolean
        {
            for(i in enrolledClasses!!) {
                if(i.isNullOrBlank())
                {
                    continue
                }
                if (i.equals(NEPTUN))
                    return true
            }
            return false
        }
        fun addClass(NEPTUN: String) :Context
        {
            if(UserDataHandler.joinClass(userID,NEPTUN, enrolledClasses))
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
        subjects = SubjectDataHandler.Subjects.distinctBy { it.NEPTUN } as ArrayList<Subject>
        adapter = SubjectAdapter(this,subjects)

        val manager = LinearLayoutManager(this)
        subjectsRV.layoutManager = manager
        subjectsRV.adapter = adapter

    }

    fun showAddSubjectDialog()
    {
        AddSubjectDialog().show(supportFragmentManager,"Dialog")
    }


}