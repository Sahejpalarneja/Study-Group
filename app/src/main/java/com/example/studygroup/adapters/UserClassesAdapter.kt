package com.example.studygroup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.data.Subjects
import com.example.studygroup.databinding.ClassCardBinding

class UserClassesAdapter(private val context: Context,classes :List<Subjects>):RecyclerView.Adapter<UserClassesAdapter.ViewHolder>() {
    private var classes = mutableListOf<Subjects>()
    init{
        this.classes.addAll(classes)
    }
    override fun onCreateViewHolder(parent : ViewGroup,viewtype:Int):ViewHolder{
        return ViewHolder(
            ClassCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return classes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = classes[position]

        holder.binding.tvSubjectName.text = currentItem.name

    }
    inner class  ViewHolder(val binding: ClassCardBinding):RecyclerView.ViewHolder(binding.root) {

    }

}