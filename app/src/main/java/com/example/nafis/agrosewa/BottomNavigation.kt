package com.example.nafis.agrosewa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.databinding.ActivityBottomNavigationBinding

class BottomNavigation : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBottomNavigationBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false
    }
}