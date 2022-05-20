package com.example.studygroup.adapters

import android.content.Context
import android.view.LayoutInflater


import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.utils.SubjectUserUtils
import com.example.studygroup.data.Message

import com.example.studygroup.databinding.ReceiveMessageBinding
import com.example.studygroup.databinding.SentLayoutBinding

class MessageAdapter(val context: Context,messageList :ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2
    private var messageList = mutableListOf<Message>()
    init {
        this.messageList.addAll(messageList)
    }
    inner class SentViewHolder(val binding:SentLayoutBinding):RecyclerView.ViewHolder(binding.root){
        val sentMessage = binding.tvSentMessage
    }
    inner class ReceiveViewHolder(val binding:ReceiveMessageBinding):RecyclerView.ViewHolder(binding.root){
        val recievedMessage = binding.tvReceivedMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      if(viewType ==1){
          return ReceiveViewHolder(
              ReceiveMessageBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              ))
      }
        else{
          return SentViewHolder(
              SentLayoutBinding.inflate(
                  LayoutInflater.from(parent.context),
                  parent,
                  false
              ))
      }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var currentMessage:Message = messageList[position]
       if(holder.javaClass == SentViewHolder::class.java){
           //do the stufff

           val viewHolder = holder as SentViewHolder

           holder.binding.tvSentMessage.text = currentMessage.message
           holder.binding.tvUsername.text = currentMessage.username
       }
        else{
           val viewHolder = holder as ReceiveViewHolder

           holder.binding.tvReceivedMessage.text =currentMessage.message
           holder.binding.tvUsername.text = currentMessage.username
            //do stuff for receive
       }
    }

    override fun getItemViewType(position: Int): Int {
       val currentMessage = messageList[position]
        if(SubjectUserUtils.getUser().Username.equals(currentMessage.username))
        {
            return  ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}