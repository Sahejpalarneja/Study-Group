package com.example.studygroup.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.nio.file.attribute.UserDefinedFileAttributeView

class UserDataHandler {
    companion object {
        val database =
            Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = SubjectDataHandler.database.getReference("Users")

        fun writeUser(user: User) {
            val emptyList = ArrayList<String>()
            emptyList.add(" ")
            ref.child(user.UserID).child("Username").setValue(user.FirstName + " " + user.LastName)
            ref.child(user.UserID).child("Classes").setValue(emptyList)
        }
        fun getUser(UserID:String):User
        {
            ref.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = snapshot.children.iterator()
                    while(users.hasNext())
                    {
                        var ID = users.next().key
                    }

                }
                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
            return User("","","","")
        }
    }
}