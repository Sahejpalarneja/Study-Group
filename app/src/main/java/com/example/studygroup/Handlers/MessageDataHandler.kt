package com.example.studygroup.Handlers

import com.example.studygroup.data.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException

class MessageDataHandler {
    companion object{
        var messages = ArrayList<Message>()
        val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app")
        private val ref = database.reference

        fun writeMessage(code:String?,message: Message)
        {
            ref.child("Data").child(code!!).child("messages").push()
                .setValue(message)
            messages.add(message)
        }
        fun loadMessages(code: String?)
        {

            ref.child("Data").child(code!!).child("messages")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        messages.clear()
                        try{
                            for (postSnapshot in snapshot.children)
                            {
                                val message = postSnapshot.getValue(Message::class.java)
                                messages.add(message!!)
                            }
                        }
                        catch(ex:NullPointerException )
                        {
                            messages= ArrayList(
                            )
                        }



                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })

        }




    }
}