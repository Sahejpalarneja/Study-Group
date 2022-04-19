package com.example.studygroup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.data.Subjects
import com.example.studygroup.databinding.SubjectCardBinding

class SubjectAdapter(private val context : Context,subjects: List<Subjects>) :RecyclerView.Adapter<SubjectAdapter.ViewHolder>(){

        private var subjects = mutableListOf<Subjects>()
        init{
           this.subjects.addAll(subjects)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                SubjectCardBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = subjects[position]
        val members = getMemberCount(currentItem.students)
        var name  = currentItem.name
        holder.binding.tvSubjectName.text = name
        var subjectCode = currentItem.NEPTUN
        holder.binding.tvSubjectCode.text = subjectCode
        holder.binding.tvProfessor.text = currentItem.professors[0]
        holder.binding.tvMembers.text =members.toString()

    }

    inner class  ViewHolder(val binding : SubjectCardBinding):RecyclerView.ViewHolder(binding.root){

    }

    private fun getMemberCount(members:ArrayList<String>):Int {
        return members.size
    }
}






