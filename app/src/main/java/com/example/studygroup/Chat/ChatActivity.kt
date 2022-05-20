package com.example.studygroup.Chat


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.studygroup.data.Message
import android.widget.EditText
import android.widget.ImageButton

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.SubjectDetails
import com.example.studygroup.utils.SubjectUserUtils
import com.example.studygroup.Handlers.MessageDataHandler
import com.example.studygroup.R
import com.example.studygroup.adapters.MessageAdapter
import com.example.studygroup.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {
    private lateinit var messageRV: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageButton
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter :MessageAdapter
    private var code: String? = null


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.subject_details_menu,menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val code = intent.getStringExtra("code")
        this.code =code
        supportActionBar?.title = name



        messageRV = binding.RVMessages
        messageBox = binding.etMessagebox
        sendButton = binding.btnSend

        adapter = MessageAdapter(this,MessageDataHandler.messages)
        messageRV.layoutManager = LinearLayoutManager(this)
        messageRV.adapter = adapter




        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message, SubjectUserUtils.getUser().Username)
            MessageDataHandler.writeMessage(code,messageObject)
            messageBox.setText("")
            adapter.notifyDataSetChanged()

        }


    }
    override fun onOptionsItemSelected(item: MenuItem) :Boolean {
        if(item.itemId == R.id.details)
        { val intent = Intent()
            intent.setClass(this, SubjectDetails::class.java)
            intent.putExtra("code",code)
            startActivity(intent)
            return true

        }
        return true
    }
}