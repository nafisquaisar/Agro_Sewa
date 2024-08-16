package com.example.nafis.agrosewa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        if(currentUser!=null){
            startActivity(Intent(this@MainActivity,Home::class.java))
            finish()
        }

        binding.welcomeSignupBtn.setOnClickListener{
            startActivity(Intent(this,SignUp::class.java))
        }
        binding.welcomeLoginBttn.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
        }


    }
}