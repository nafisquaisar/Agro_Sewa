package com.example.nafis.agrosewa

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.nafis.agrosewa.Data.UserDetail
import com.example.nafis.agrosewa.databinding.ActivityHomeBinding
import com.example.nafis.agrosewa.fragment.AgroShop
import com.example.nafis.agrosewa.fragment.Community
import com.example.nafis.agrosewa.fragment.Profile
import com.example.nafis.agrosewa.fragment.myplant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setting.setOnClickListener{
            startActivity(Intent(this,Setting::class.java))
        }
        binding.chatbot.setOnClickListener{
            startActivity(Intent(this,Chatbot::class.java))
        }

        binding.notification.setOnClickListener{
            startActivity(Intent(this,Notification::class.java))
        }


        replaceFragment(myplant())
       binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    // put your code here
                    replaceFragment(myplant())
                }
                R.id.shop -> {
                    // put your code here
                    replaceFragment(AgroShop())
                }
                R.id.commubnity -> {
                    // put your code here
                    replaceFragment(Community())
                }
                R.id.profile -> {
                    // put your code here
                    replaceFragment(Profile())
                }
                else->{

                }
            }
            true
        }

        binding.fab.setOnClickListener { startActivity(Intent(this,Camera::class.java)) }

        // set Home Name
       sethomename()

    }

    private fun sethomename() {

        lifecycleScope.launch {
            try {
                val database = FirebaseDatabase.getInstance()
                val uid= FirebaseAuth.getInstance().currentUser?.uid.toString()
                val myRef = database.getReference("Users").child(uid)
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val value = dataSnapshot.getValue(UserDetail::class.java)
                        val username=value?.username
                        val firstName = username?.split(" ")?.firstOrNull() ?: ""
                        binding.homeNameTop.text = firstName

                    }
                    override fun onCancelled(error: DatabaseError) {
                        println("Failed to read value: ${error.toException()}")
                    }
                })
            }catch (e:Exception){
                progress.toastShow(this@Home,e.message.toString())
            }
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
    }
}