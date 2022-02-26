package com.example.studygroup.main



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast

import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.R
import com.example.studygroup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        val SearchListener : SearchView = findViewById(R.id.search)
        SearchListener.queryHint ="Search through Neptun Code"
        SearchListener.setOnQueryTextListener(SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query:String?):Boolean{
                return true
            }
            override fun onQueryTextChange(newText:String?)
            {
                return true
            }
        })



        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SubjectDataHandler.ReadFromDataBase()


    }

}