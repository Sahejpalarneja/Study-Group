package com.example.studygroup.data

import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import com.example.studygroup.main.MainActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class SubjectDataHandler {

    //val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")

    companion object {
        var  Subjects = ArrayList<Subjects>()
        val SuggestionList =  ArrayList<String>()
        val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = database.getReference("Subjects")
        //val cursorAdapter = SimpleCursorAdapter(MainActivity,MainActivity.getSearchView())
        fun InitializeSubjects(){

            ref.addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val subjects = snapshot.children.iterator()
                    while (subjects.hasNext())
                    {

                        var subject= subjects.next().value.toString()
                        var neptun = JSONObject(subject)
                        var name= neptun.get("NEPTUN")
                        //var professors = subject.get("professors")
                        //var name = subject.child("name").value.toString()
                        //var students = subject.child("students").value

                        //Subjects.add(Subjects("neptun",name,
                           // professors as ArrayList<String>,
                            //students as ArrayList<String>

                        //))
                        //SuggestionList.add(name)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
        }
        fun FindSubject(query:String){
            for(subject in Subjects)
            {
                if (query.equals(subject.name) or query.equals(subject.NEPTUN))
                {

                }
            }
        }

    }


}