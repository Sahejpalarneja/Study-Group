package com.example.studygroup.data

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SubjectDataHandler {


companion object {

    val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
    var ref = database.getReference("Subjects").child("0").child("NEPTUN").child("name")
    public lateinit var value :String
    public fun ReadFromDataBase(){
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                value = snapshot.getValue<String>().toString()
                System.out.println(value)
                Log.d(TAG,"This is it: "+value)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }
}

}