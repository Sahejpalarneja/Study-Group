package com.example.studygroup.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserDataHandler {
    companion object {
        val database =
            Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = database.getReference("Users")

        fun writeUser(user: User) {
            ref.child(user.UserID).child("Username").setValue(user.Username)
            ref.child(user.UserID).child("Classes").setValue(ArrayList<String>())
        }
        fun getUser(UserID:String):User
        {
            val list = arrayListOf<String>("NEPTUN")
            var currentUser= User("AFA1bYYLV1WYvjhVPXOXSfCHAQe2","test",list)
            ref.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = snapshot.children.iterator()
                    while(users.hasNext())
                    {
                        val ID = users.next().key.toString()
                        if(ID.equals(UserID))
                        {
                            val name  = users.next().child("Username").value.toString()
                            val classes = users.next().child("Classes").value
                            currentUser = User(ID,name,classes as ArrayList<String>)
                        }

                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
            return currentUser
        }
    }
}