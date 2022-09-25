package com.example.studygroup.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.models.Subject
import com.example.studygroup.databinding.SubjectCardBinding
import com.example.studygroup.main.MainActivity
import com.example.studygroup.menu_options.FindClassActivity
import com.example.studygroup.utils.SubjectUserUtils

class SubjectAdapter(private val context : Context,subjects: List<Subject>) :RecyclerView.Adapter<SubjectAdapter.ViewHolder>(){

        private var subjects = mutableListOf<Subject>()
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
        //val members = getMemberCount(currentItem.students)
        holder.binding.tvSubjectName.text = currentItem.name
        holder.binding.tvSubjectCode.text = currentItem.neptun
        holder.binding.tvProfessor.text = currentItem.professor
        //holder.binding.tvMembers.text =members.toString()
        holder.binding.btnJoin.setOnClickListener {

            if(!SubjectUserUtils.checkDuplicate(currentItem.neptun))
            {
                FindClassActivity.joinSubject(currentItem.neptun)
                val context = FindClassActivity.getContext()
                val mainIntent = Intent()
                mainIntent.setClass(context,MainActivity::class.java)
                context.startActivity(mainIntent)


            }
            else {

                //TODO add error message
                val context = FindClassActivity.alreadyEnrolled()
                val mainIntent = Intent()
                mainIntent.setClass(context,MainActivity::class.java)
                context.startActivity(mainIntent)

            }

        }


    }

    inner class  ViewHolder(val binding : SubjectCardBinding):RecyclerView.ViewHolder(binding.root) {

    }

    private fun getMemberCount(members:ArrayList<String>):Int {
        return members.size
    }

}






