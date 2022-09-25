package com.example.studygroup.main
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.example.studygroup.utils.SubjectUserUtils
import com.example.studygroup.R
import com.example.studygroup.adapters.UserClassesAdapter

import com.example.studygroup.models.Subject
import com.example.studygroup.databinding.ActivityMainBinding
import com.example.studygroup.menu_options.FindClassActivity
import com.example.studygroup.login.LoginActivity


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private  lateinit var classes : ArrayList<Subject>
    private lateinit var classesRV : RecyclerView
    private lateinit var adapter : UserClassesAdapter

    private var userClasses :ArrayList<String>? = null
    private lateinit var refreshListener: SwipeRefreshLayout.OnRefreshListener



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.join_class_menu,menu)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = SubjectUserUtils.getUser().username
        userClasses = SubjectUserUtils.getSubjectNames()


        classesRV = binding.recyclerItem
        buildRecyclerView()

        refreshListener = SwipeRefreshLayout.OnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing= true
            supportActionBar?.title = SubjectUserUtils.getUser().username
            buildRecyclerView()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener(refreshListener)


    }

    override fun onOptionsItemSelected(item: MenuItem) :Boolean {
        if(item.itemId == R.id.find_class)
        { val intent = Intent()
            intent.setClass(this,FindClassActivity::class.java)
            startActivity(intent)
            return true

        }
        else if(item.itemId == R.id.logout)
        {

            val intent = Intent()
            intent.setClass(this,LoginActivity::class.java)
            startActivity(intent)

           return true
        }

    return true
    }
    private fun buildRecyclerView()
    {
        classes = SubjectUserUtils.getSubjects()
        adapter = UserClassesAdapter(this,classes)

       val manager = LinearLayoutManager(this)
        classesRV.layoutManager = manager
        classesRV.adapter = adapter
    }

}


