package com.example.studygroup.ButtonActivities

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.studygroup.data.SubjectDataHandler
import com.example.studygroup.data.UserDataHandler
import com.example.studygroup.databinding.AddSubjectDialogBinding
import com.example.studygroup.main.MainActivity
import com.example.studygroup.menu_options.FindClassActivity

class AddSubjectDialog:DialogFragment() {

    interface SubjectHandler{


    }
    private lateinit var  subjectHandler :SubjectHandler

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        if(context is SubjectHandler)
        {
            subjectHandler = context
        }
        else
        {
            throw RuntimeException("The Activity is not implementing the handler")
        }

    }

    private lateinit var etSubjectName:EditText
    private lateinit var etSubjectCode:EditText
    private lateinit var etProfessorName : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setTitle("Add Subject")
        val dialogBinding = AddSubjectDialogBinding.inflate(layoutInflater)

        etSubjectName = dialogBinding.etSubjectName
        etSubjectCode = dialogBinding.etSubjectCode
        etProfessorName = dialogBinding.etProfessorName

        dialogBuilder.setView(dialogBinding.root)
        dialogBuilder.setPositiveButton("Ok"){_,_ ->}
        dialogBuilder.setNegativeButton("Cancel"){_,_->}
        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()
        val positiveButon = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButon.setOnClickListener {
            if(etSubjectName.text.isNotEmpty())
            {
                if(etSubjectCode.text.isNotEmpty())
                {
                    val professorList = ArrayList<String>()
                    val students = ArrayList<String>()
                    if(etProfessorName.text.isNotEmpty())
                    {
                        professorList.add(etProfessorName.text.toString())
                    }
                    val currentUser = SubjectUserUtils.getUser().Username.toString()
                    students.add(currentUser)

                    val newSubject= com.example.studygroup.data.Subject(etSubjectCode.text.toString(),etSubjectName.text.toString(),professorList,students)
                    SubjectDataHandler.writeSubject(newSubject)
                    UserDataHandler.joinClass(SubjectUserUtils.getUser().UserID,etSubjectCode.text.toString(),SubjectUserUtils.getUser().Classes,)
                    val mainIntent = Intent()
                    mainIntent.setClass(FindClassActivity.Companion.getContext(),MainActivity::class.java)
                    startActivity(mainIntent)
                    dialog!!.dismiss()


                }
                else{
                    etSubjectCode.error = "This cannot be empty"

                }
            }
            else{
                etSubjectName.error = "This cannot be empty"
            }
        }
    }
}