package com.example.studygroup.ButtonActivities

import com.example.studygroup.data.Subject
import com.example.studygroup.data.User

class SubjectUserUtils {



    companion object{
        private lateinit var subjects : ArrayList<Subject>
        private lateinit var user : User

        fun setSubjects(subjects:ArrayList<Subject>)
        {
            this.subjects = subjects
        }
        fun setUser(user:User)
        {
            this.user = user
        }
        fun getSubjects(): ArrayList<Subject> {
            return this.subjects
        }
        fun getUser(): User {
            return this.user
        }
        fun getSubjectFromCode(code:String?):Subject {
            for(i in subjects)
            {
                if (i.NEPTUN.equals(code))
                {
                    return i
                }
            }
            return Subject("","", ArrayList(), ArrayList())
        }

    }



}