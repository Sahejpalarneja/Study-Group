package com.example.studygroup.utils

import com.example.studygroup.Handlers.SubjectDataHandler
import com.example.studygroup.models.Subject
import com.example.studygroup.models.AuthUser

class SubjectUserUtils {



    companion object{
        private lateinit var subjects : ArrayList<Subject>
        private lateinit var user : AuthUser

        fun setSubjects(subjects:ArrayList<Subject>){
            Companion.subjects = subjects
        }
        fun setUser(user: AuthUser) {
            Companion.user = user
        }
        fun getSubjects(): ArrayList<Subject> {
            return subjects
        }
        fun getUser(): AuthUser {
            return user
        }
        fun getSubjectFromCode(code:String?):Subject {
            for(i in subjects)
            {
                if (i.neptun.equals(code))
                {
                    return i
                }
            }
            return Subject("","", "")
        }
        fun getSubjectNames():ArrayList<String>{
            var result = ArrayList<String>()
            for(i in subjects){
                result.add(i.name)
            }
            return result
        }
        fun checkDuplicate(code:String):Boolean {
            if(subjects.isEmpty())
            {
                return false
            }
            for(i in subjects)
            {
                if(code == i.neptun)
                {
                    return true
                }
            }
            return false
        }

    }



}