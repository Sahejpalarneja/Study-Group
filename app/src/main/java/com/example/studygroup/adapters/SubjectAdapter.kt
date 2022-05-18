package com.example.studygroup.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.data.Subject
import com.example.studygroup.databinding.SubjectCardBinding
import com.example.studygroup.main.MainActivity
import com.example.studygroup.menu_options.FindClassActivity

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
        val members = getMemberCount(currentItem.students)
        holder.binding.tvSubjectName.text = currentItem.name
        holder.binding.tvSubjectCode.text = currentItem.NEPTUN
        holder.binding.tvProfessor.text = currentItem.professors[0]
        holder.binding.tvMembers.text =members.toString()
        holder.binding.btnJoin.setOnClickListener {

            if(!FindClassActivity.checkIfEnrolled(currentItem.NEPTUN))
            {
                val context = FindClassActivity.addClass(currentItem.NEPTUN)
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






