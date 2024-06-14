package com.myandroid.blinkitclone

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.material3.ProgressIndicatorDefaults
import com.google.firebase.auth.FirebaseAuth
import com.myandroid.blinkitclone.databinding.ProgressDialogBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {
    private var dialog:AlertDialog?=null

    fun showDialog(context: Context,message: String)
    {
        val progress = ProgressDialogBinding.inflate(LayoutInflater.from(context))
        progress.tvMessage.text=message
        dialog=AlertDialog.Builder(context).setView(progress.root).setCancelable(false).create()
        dialog!!.show()

    }

    fun hideDialog(){
        dialog?.dismiss()
    }



    fun showToast(context: Context, message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    private var firebaseAuthInstance:FirebaseAuth?=null
    fun getAuthInstance():FirebaseAuth{
        if(firebaseAuthInstance==null)
        {
            firebaseAuthInstance= FirebaseAuth.getInstance()
        }
        return firebaseAuthInstance!!
    }

    fun getCurrentUserId(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.uid
    }

    fun getRandomId(): String {
        return (1..25).map { (('A'..'Z') + ('a'..'z') + ('0'..'9')).random() }.joinToString("")


    }

    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return currentDate.format(formatter)


    }



}