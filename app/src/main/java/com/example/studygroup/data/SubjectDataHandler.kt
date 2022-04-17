

package com.example.studygroup.data

import android.content.ContentValues.TAG

import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase

@Suppress("UNCHECKED_CAST")
class SubjectDataHandler {

    companion object {
        var  Subjects = ArrayList<Subjects>()
        val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = database.getReference("Subjects")

        fun InitializeSubjects(){

            ref.addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot){
                    val subjects = snapshot.children.iterator()
                    while (subjects.hasNext())
                    {
                        var subject= listOf(subjects.next().children)[0]


                        for(neptun in subject)
                        {
                            var code:String? = neptun.key
                            var name :String? = neptun.child("name").value.toString()
                            var professors = neptun.child("professors").value
                            var students = neptun.child("students").value
                            Subjects.add(Subjects(code as String,name as String, professors as ArrayList<String>, students as ArrayList<String>))
                        }
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
