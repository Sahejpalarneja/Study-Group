package com.example.studygroup.Chat


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.studygroup.data.Message
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.studygroup.ButtonActivities.SubjectUserUtils
import com.example.studygroup.Handlers.MessageDataHandler
import com.example.studygroup.adapters.MessageAdapter
import com.example.studygroup.databinding.ActivityChatBinding


class ChatActivity : AppCompatActivity() {
    private lateinit var messageRV: RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sendButton:ImageButton
    private lateinit var binding: ActivityChatBinding
    private lateinit var adapter :MessageAdapter
    private lateinit var messages : ArrayList<Message>

    val receiveRoom:String? = null
    val senderRoom:String? = null
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
        messages = ArrayList()
        adapter = MessageAdapter(this,messages)



        sendButton.setOnClickListener{
            val message = messageBox.text.toString()
            val messageObject = Message(message,SubjectUserUtils.getUser().UserID)
            MessageDataHandler.writeMessage(code,messageObject)

        }




    }
}