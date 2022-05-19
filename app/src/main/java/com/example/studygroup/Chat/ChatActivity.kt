package com.example.studygroup.Chat


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studygroup.data.Message
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.SubjectUserUtils
import com.example.studygroup.Handlers.MessageDataHandler
import com.example.studygroup.adapters.MessageAdapter
import com.example.studygroup.databinding.ActivityChatBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.Thread.sleep


class ChatActivity : AppCompatActivity() {
    private lateinit var messageRV: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageButton
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter :MessageAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name = intent.getStringExtra("name")
        val code = intent.getStringExtra("code")
        supportActionBar?.title = name



        messageRV = binding.RVMessages
        messageBox = binding.etMessagebox
        sendButton = binding.btnSend

        adapter = MessageAdapter(this,MessageDataHandler.messages)
        messageRV.layoutManager = LinearLayoutManager(this)
        messageRV.adapter = adapter




        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message,SubjectUserUtils.getUser().UserID)
            MessageDataHandler.writeMessage(code,messageObject)
            messageBox.setText("")
            adapter.notifyDataSetChanged()

        }



    }
}