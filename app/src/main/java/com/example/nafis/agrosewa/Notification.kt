package com.example.nafis.agrosewa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.databinding.ActivityNotificationBinding

class Notification : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNotificationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.alerttoolbar.setNavigationOnClickListener{onBackPressed()}

        binding.alerttoolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.alertsetting->{
                    startActivity(Intent(this,Setting::class.java))
                }
                else->{

                }
            };true
        }
    }
}