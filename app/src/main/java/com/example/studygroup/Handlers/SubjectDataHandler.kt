package com.example.studygroup.Handlers

import com.example.studygroup.models.Subject



@Suppress("UNCHECKED_CAST")
class SubjectDataHandler {

    companion object {
        private lateinit var Subjects:ArrayList<Subject>

        fun setSubjects(subjects:ArrayList<Subject>) {
            Subjects = subjects
        }

        fun getSubjects():ArrayList<Subject> {
            return Subjects
        }

        fun getClassFromCode(neptun:String): Subject {
            val result = Subject("","","")
            for(subject in Subjects)
            {
                if(subject.neptun  == neptun)
                {
                    return subject
                }
            }
            return result
        }

        fun addSubject(newSubject:Subject){
            Subjects.add(newSubject)
        }

        fun checkDuplicate(code:String):Boolean {
            if(Subjects.isEmpty())
            {
                return false
            }
            for(i in Subjects)
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
