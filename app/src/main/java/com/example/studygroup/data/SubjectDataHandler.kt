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
import java.lang.Exception

class SubjectDataHandler {
    private var  Subjects = ArrayList<Subjects>()
    val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
    private fun InitializeSubjects(){
        val ref = database.getReference("Subjects")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val subjects = snapshot.children.iterator()
                while (subjects.hasNext())
                {
                    var neptun = subjects.next().key.toString()
                    var subject = subjects.next().child(neptun)
                    var professors = subject.child("professors").value
                    var name = subject.child("name").value.toString()
                    var students = subject.child("students").value

                    Subjects.add(Subjects(neptun,name,
                        professors as ArrayList<String>,
                        students as ArrayList<String>
                    ))

                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

companion object {
    val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
    var ref = database.getReference("Subjects")
    public lateinit var value :String
    public fun ReadFromDataBase(){
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val subjects = snapshot.children.iterator()
                while (subjects.hasNext())
                {
                    var subject: DataSnapshot
                    try{
                        subject = subjects.next().child("NEPTUN")
                    }
                    catch (e :Exception)
                    {
                        subject = subjects.next().child("NEPTUN2")
                    }


                    Log.i("Subject Info","name"+subject.child("name").value.toString())



                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }


    }


}