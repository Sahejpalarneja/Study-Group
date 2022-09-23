package com.example.studygroup.utils

import com.example.studygroup.models.Subject
import com.example.studygroup.data.User
import com.example.studygroup.models.AuthUser

class SubjectUserUtils {



    companion object{
        private lateinit var subjects : ArrayList<Subject>
        private lateinit var user : AuthUser

        fun setSubjects(subjects:ArrayList<Subject>)
        {
            Companion.subjects = subjects
        }
        fun setUser(user: AuthUser)
        {
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

    }



}