

package com.example.studygroup.data

import android.content.ContentValues.TAG
import com.example.studygroup.ButtonActivities.SubjectUserUtils

import android.util.Log
import android.widget.Toast

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

        fun InitializeSubjects() {

            ref.addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val subjects = snapshot.children.iterator()
                    while (subjects.hasNext()) {
                        var subject = listOf(subjects.next().children)[0]


                        for (neptun in subject) {
                            var code: String = neptun.key.toString()
                            if (checkDuplicate(code)) break
                            var name: String? = neptun.child("name").value.toString()
                            var professors = neptun.child("professors").value
                            var students = neptun.child("students").value
                            Subjects.add(
                                Subjects(
                                    code as String,
                                    name as String,
                                    professors as ArrayList<String>,
                                    students as ArrayList<String>
                                )
                            )
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read value.", error.toException())
                }
            })
            SubjectUserUtils.setSubjects(Subjects)

        }
        fun checkDuplicate(code:String):Boolean
        {
            if(Subjects.isEmpty())
            {
                return false
            }
            for(i in Subjects)
            {
                val temp = i.NEPTUN
                if(code == i.NEPTUN)
                {
                    return true
                }
            }
            return false
        }
        fun FindSubject(query:String){
            for(subject in Subjects)
            {
                if (query.equals(subject.name) or query.equals(subject.NEPTUN))
                {

                }
            }
        }
        fun getUserClasses(userClasses: ArrayList<String>?): ArrayList<Subjects> {
            val result = ArrayList<Subjects>()
            if (userClasses != null) {
                var tt = Subjects
                for(i in userClasses) {
                    for(j in Subjects) {
                        var temp = j.NEPTUN.toString()
                        if (i == j.NEPTUN.toString()) {
                            result.add(j)
                        }
                    }
                }
            }
            return result
        }
    }


}
