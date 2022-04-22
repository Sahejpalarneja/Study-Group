package com.example.studygroup.main
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.R
import com.example.studygroup.adapters.UserClassesAdapter
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.data.Subjects
import com.example.studygroup.databinding.ActivityMainBinding
import com.example.studygroup.menu_options.FindClassActivity

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var classes : ArrayList<Subjects>
    private lateinit var classesRV : RecyclerView
    private lateinit var adapter : UserClassesAdapter


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.join_class_menu,menu)
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        classesRV = binding.recyclerItem
        buildRecyclerView()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent()
        intent.setClass(this,FindClassActivity::class.java)
        startActivity(intent)
        return true

    }
    private fun buildRecyclerView()
    {
        val userClasses = intent.getStringArrayListExtra("Classes")
        classes = SubjectDataHandler.getUserClasses(userClasses)
        adapter = UserClassesAdapter(this,classes)

       val manager = LinearLayoutManager(this)
        classesRV.layoutManager = manager
        classesRV.adapter = adapter
    }

}


