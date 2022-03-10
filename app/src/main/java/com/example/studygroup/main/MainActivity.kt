package com.example.studygroup.main



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView

//import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.R
import com.example.studygroup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu,menu)
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val simple =  findViewById<SearchView>(R.id.search)
        simple.setOnQueryTextListener( object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(this@MainActivity,"Changed",Toast.LENGTH_SHORT).show()
                return false

            }

            override fun onQueryTextSubmit(p0: String?): Boolean {

                Toast.makeText(this@MainActivity,"Submitted",Toast.LENGTH_SHORT).show()
                return true
            }
        })



    }

}