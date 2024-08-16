package com.example.nafis.agrosewa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.nafis.agrosewa.databinding.ActivityLoginBinding
import com.example.nafis.agrosewa.databinding.ForgotPassLayoutBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.loginSignupBtn.setOnClickListener{
            startActivity(Intent(this,SignUp::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener{
            loginprocess()
        }
        binding.loginForgotbtn.setOnClickListener{
            forgotProcess()
        }
    }

    private fun forgotProcess() {
        val dialog=ForgotPassLayoutBinding.inflate(LayoutInflater.from(this))
        val alertDialog=AlertDialog.Builder(this)
            .setView(dialog.root)
            .create()

        alertDialog.show()
        dialog.forgotbtn.setOnClickListener{
            val email=dialog.fogotemail.editText?.text.toString()
            if(email.isNotEmpty()){
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                progress.toastShow(this@Login,"Please Check your email")
                alertDialog.hide()
            }else{
                progress.toastShow(this,"Enter the Email")
            }
        }
        dialog.forgotcancel.setOnClickListener{
            alertDialog.hide()
        }
    }

    private fun loginprocess() {
        var email=binding.loginEmail.editText?.text.toString()
        var pasword=binding.loginPassword.editText?.text.toString()

        if(email.isNotEmpty() && pasword.isNotEmpty()){
                login(email,pasword)
        }else{
              progress.toastShow(this,"Enter empty field")
        }
    }

    private fun login(email: String, pasword: String) {
        progress.show(this)

//lifecycleScope.launch {
//    try{
//           val firebaseauth=FirebaseAuth.getInstance()
//           val authresult=firebaseauth.signInWithEmailAndPassword(email,pasword).await()
//           val currentUser=authresult.user?.uid
//           val verifyEmail=firebaseauth.currentUser?.isEmailVerified
//           if(verifyEmail==true){
//               if(currentUser!=null){
//                   FirebaseDatabase.getInstance().getReference("Users").child(currentUser).addListenerForSingleValueEvent(object :ValueEventListener{
//                       override fun onDataChange(snapshot: DataSnapshot) {
//                               startActivity(Intent(this@Login,Home::class.java))
//                               finish()
//                       }
//
//                       override fun onCancelled(error: DatabaseError) {
//                           progress.hide()
//                           progress.toastShow(this@Login,error.message)
//                       }
//
//                   })
//               }
//           }else{
//               progress.hide()
//               progress.toastShow(this@Login,"Verify Email")
//           }
//    }catch (e: Exception){
//        progress.hide()
//        progress.toastShow(this@Login,e.message.toString())
//    }
//}


        lifecycleScope.launch {
            try {
                val auth=FirebaseAuth.getInstance()


                auth.signInWithEmailAndPassword(email,pasword).addOnCompleteListener {
                    val verifyEmail=auth.currentUser?.isEmailVerified
                    if(verifyEmail==true){
                      if (it.isSuccessful){
                        startActivity(Intent(this@Login,Home::class.java))
                          finish()
                      }else{
                        progress.hide()
                        progress.toastShow(this@Login,"Wrong Email or Password")
                      }
                    }else{
                        progress.hide()
                        progress.toastShow(this@Login,"Please Verify email")
                    }
                }
            }catch (e: Exception){
            progress.hide()
            progress.toastShow(this@Login,e.message.toString())
            }
        }
    }



}