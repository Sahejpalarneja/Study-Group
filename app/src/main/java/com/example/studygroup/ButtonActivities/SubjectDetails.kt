package com.example.studygroup.ButtonActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.adapters.StudentAdapter
import com.example.studygroup.adapters.UserClassesAdapter
import com.example.studygroup.data.Subject
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.databinding.ActivitySubjectDetailsBinding

class SubjectDetails : AppCompatActivity() {
    private lateinit var binding :ActivitySubjectDetailsBinding
    private lateinit var studentsRV : RecyclerView
    private lateinit var adapter : StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val code  = intent.getStringExtra("code")
        val subject = SubjectUserUtils.getSubjectFromCode(code)
        binding.tvSubjectName.text =subject.name.toString()
        binding.tvSubjectCode.text = code
        binding.tvProfessorName.text = subject.professors[0]

        studentsRV = binding.RVStudents
        buildRecyclerView(subject.students)



    }
    private fun buildRecyclerView(students:ArrayList<String>)
    {

        adapter = StudentAdapter(this,students)

        val manager = LinearLayoutManager(this)
        studentsRV.layoutManager = manager
        studentsRV.adapter = adapter
    }

}