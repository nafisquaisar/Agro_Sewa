package com.example.nafis.agrosewa

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.nafis.agrosewa.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth

class Setting : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySettingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.settinglogout.setOnClickListener{
            processlogout()
        }
        binding.toolbar.apply {
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

    }

    private fun processlogout() {
        val builder=AlertDialog.Builder(this)
        val alertDialog=builder.create()

        builder.setTitle("LogOut")
            .setMessage("Are you sure want to Logout")
            .setPositiveButton("Yes"){_,_->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,Login::class.java))
                finish()
            }
            .setNegativeButton("No"){_,_->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }
}