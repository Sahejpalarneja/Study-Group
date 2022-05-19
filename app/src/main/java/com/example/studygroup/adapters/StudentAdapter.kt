package com.example.studygroup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.studygroup.databinding.StudentCardBinding


class StudentAdapter(private val context : Context, _students: ArrayList<String>) : RecyclerView.Adapter<StudentAdapter.ViewHolder>(){

    private var students = mutableListOf<String>()
    init{
        this.students.addAll(_students)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(StudentCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        )
    }
    override fun getItemCount(): Int {
        return students.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentStudent = students[position]
        holder.binding.tvStudentName.text = currentStudent



    }

    inner class  ViewHolder(val binding :StudentCardBinding) :RecyclerView.ViewHolder(binding.root) {

    }



}