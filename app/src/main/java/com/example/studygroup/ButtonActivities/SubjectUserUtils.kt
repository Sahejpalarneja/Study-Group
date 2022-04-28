package com.example.studygroup.ButtonActivities

import com.example.studygroup.data.Subjects
import com.example.studygroup.data.User

class SubjectUserUtils {
    private lateinit var subjects : ArrayList<Subjects>
    private lateinit var user : User


    companion object{
        private lateinit var subjects : ArrayList<Subjects>
        private lateinit var user : User

        fun setSubjects(subjects:ArrayList<Subjects>)
        {
            this.subjects = subjects
        }
        fun setUser(user:User)
        {
            this.user = user
        }
        fun getSubjects(): ArrayList<Subjects> {
            return this.subjects
        }
        fun getUser(): User {
            return this.user
        }

    }



}