package com.example.nafis.agrosewa

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import com.example.nafis.agrosewa.Data.UserDetail
import com.example.nafis.agrosewa.databinding.ActivitySignUpBinding
import com.example.nafis.agrosewa.databinding.SucceesLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.signupLoginBtn.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
            finish()
        }

        binding.SignUpbtn.setOnClickListener{
            signUpProcess()
        }
    }

    private fun signUpProcess() {
         val email= binding.signupEmail.editText?.text.toString()
         val name=binding.signupName.editText?.text.toString()
         val number= binding.signupNum.editText?.text.toString()
         val password=binding.signupPass.editText?.text.toString()
         val cnfPassword= binding.signupCnfPass.editText?.text.toString()

        if(email.isNotEmpty() && name.isNotEmpty() && number.isNotEmpty() && password.isNotEmpty() && cnfPassword.isNotEmpty()){
            if(cnfPassword==password){
                saveuserData(email,number,password,name)
            }else{
                progress.toastShow(this,"Fill Same Password")
            }
        }else{
            progress.toastShow(this,"Fill all the Field")
        }
    }

    private fun saveuserData(email: String, number: String, password: String,name:String) {
             val firebaseAuth=FirebaseAuth.getInstance()
             val db=FirebaseDatabase.getInstance().getReference("Users")
         try {
             firebaseAuth.createUserWithEmailAndPassword(email,password)
             // ======== Email Verification Work  =========
             FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {
              val dialog=SucceesLayoutBinding.inflate(LayoutInflater.from(this))
              val alertDialog=AlertDialog.Builder(this)
                  .setView(dialog.root)
                  .create()
              progress.hide()
              alertDialog.show()
                 dialog.btnOKCreate.setOnClickListener{
                     alertDialog.dismiss()
                     startActivity(Intent(this,Login::class.java))
                     finish()
                 }
             }

             val uid=firebaseAuth.currentUser?.uid.toString()
             val userDetail=UserDetail(uid,email,number,password,name,"")
             db.child(uid).setValue(userDetail)

         }catch (e:Exception){
                progress.hide()
                progress.toastShow(this@SignUp,e.message.toString())
         }
    }


}