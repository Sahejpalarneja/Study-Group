package com.example.studygroup.main



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
//import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.example.studygroup.R

import com.example.studygroup.data.SubjectDataHandler
//import com.example.studygroup.R
import com.example.studygroup.databinding.ActivityMainBinding
import com.example.studygroup.menu_options.FindClassActivity

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.join_class_menu,menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       SubjectDataHandler.InitializeSubjects()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent()
        intent.setClass(this,FindClassActivity::class.java)
        startActivity(intent)
        return true

    }

}


