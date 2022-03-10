package com.example.studygroup.main



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.SearchView

//import com.example.studygroup.data.SubjectDataHandler
//import com.example.studygroup.R
import com.example.studygroup.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
             android.widget.SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String?): Boolean {
                 Toast.makeText(this@MainActivity,"Changed",Toast.LENGTH_SHORT).show()
                 return true
             }

             override fun onQueryTextChange(newText: String?): Boolean {
                 Toast.makeText(this@MainActivity,"Submitted",Toast.LENGTH_SHORT).show()
                 return true
             }
         })



    }

}


