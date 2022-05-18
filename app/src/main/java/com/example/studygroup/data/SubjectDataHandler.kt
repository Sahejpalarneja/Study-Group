

package com.example.studygroup.data

import android.content.ContentValues.TAG
import com.example.studygroup.ButtonActivities.SubjectUserUtils

import android.util.Log

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase

@Suppress("UNCHECKED_CAST")
class SubjectDataHandler {

    companion object {
        var  Subjects = ArrayList<com.example.studygroup.data.Subject>()
        val database = Firebase.database("https://study-group-c445e-default-rtdb.europe-west1.firebasedatabase.app/")
        val ref = database.getReference("Subjects")

        fun InitializeSubjects() {

            ref.addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val subjects = snapshot.children.iterator()
                    while (subjects.hasNext()) {
                        var subject =subjects.next()
                        var code = subject.child("neptun").value
                        var name = subject.key
                        var professors = subject.child("professors").value
                        var students = subject.child("students").value// listOf(subjects.next().children)[0]
                        try {
                            Subjects.add(
                                Subject(
                                    code.toString(),
                                    name.toString(),
                                    professors as ArrayList<String>,
                                    students as ArrayList<String>
                                )
                            )
                        }
                        catch (ex:NullPointerException){
                            continue
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
        fun getUserClasses(userClasses: ArrayList<String>?): ArrayList<com.example.studygroup.data.Subject> {
            val result = ArrayList<com.example.studygroup.data.Subject>()
            if (userClasses != null) {
                for(i in userClasses) {
                    for(j in Subjects) {
                        if (i == j.NEPTUN.toString()) {
                            result.add(j)
                        }
                    }
                }
            }
            return result
        }

        fun writeSubject(newSubject: com.example.studygroup.data.Subject)
        {
            ref.child(newSubject.name.toString())
            ref.child(newSubject.name.toString()).child("neptun").setValue(newSubject.NEPTUN)
            ref.child(newSubject.name.toString()).child("professors").setValue(newSubject.professors )
            ref.child(newSubject.name.toString()).child("students").setValue(newSubject.students)

        }
        fun getClass(NEPTUN:String): com.example.studygroup.data.Subject {
            var result = Subject(" "," ", ArrayList<String>(), ArrayList<String>())
            for(subject in Subjects)
            {
                if(subject.NEPTUN.toString()  == NEPTUN)
                {
                    result= subject;
                }
            }
            return result

        }
        fun writeUsertoSubject(subject:String,userID:String)
        {
            var joinedSubject= getClass(subject )
            var studentList = joinedSubject.students
            studentList.add(userID)

            ref.child(joinedSubject.name!!).child("students").setValue(studentList)


        }
    }


}
