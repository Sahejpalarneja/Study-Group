package com.example.studygroup.menu_options

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.R
import com.example.studygroup.adapters.SubjectAdapter
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.data.Subjects
import com.example.studygroup.databinding.ActivityFindClassBinding

class FindClassActivity : AppCompatActivity() {

    private lateinit var binding:ActivityFindClassBinding
    private lateinit var  subjectsRV:RecyclerView
    private lateinit var adapter:SubjectAdapter
    private lateinit var subjects:ArrayList<Subjects>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindClassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subjectsRV = binding.RVSubjects

        buildRecyclerView()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu?.findItem(R.id.search)



        return true
    }
    private fun buildRecyclerView(){
        subjects = SubjectDataHandler.Subjects
        adapter = SubjectAdapter(this,subjects)

        val manager = LinearLayoutManager(this)
        subjectsRV.layoutManager = manager
        subjectsRV.adapter = adapter

    }
}