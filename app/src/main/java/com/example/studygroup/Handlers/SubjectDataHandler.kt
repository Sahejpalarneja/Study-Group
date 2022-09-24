

package com.example.studygroup.Handlers


import com.example.studygroup.models.Subject


@Suppress("UNCHECKED_CAST")
class SubjectDataHandler {

    companion object {
        private lateinit var Subjects:ArrayList<Subject>

        fun setSubjects(subjects:ArrayList<Subject>)
        {
            Subjects = subjects
        }
        fun getSubjects():ArrayList<Subject>
        {
            return Subjects
        }
        /*
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
        fun getUserClasses(userClasses: ArrayList<String>?): ArrayList<Subject> {
            val result = ArrayList<Subject>()
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

        fun writeSubject(newSubject: Subject)
        {
            ref.child(newSubject.name.toString())
            ref.child(newSubject.name.toString()).child("neptun").setValue(newSubject.NEPTUN)
            ref.child(newSubject.name.toString()).child("professors").setValue(newSubject.professors )
            ref.child(newSubject.name.toString()).child("students").setValue(newSubject.students)

        }
        fun getClass(NEPTUN:String): Subject {
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
        */
    }


}
