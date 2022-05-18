package com.example.studygroup.Handlers

import com.example.studygroup.data.Message
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MessageDataHandler {
    companion object{
        var messages = ArrayList<Message>()
        val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app")
        private val ref = database.reference

        fun writeMessage(code:String?,message: Message)
        {
            ref.child("Data").child(code!!).child("messages").push()
                .setValue(message)
        }



    }
}