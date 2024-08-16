package com.example.nafis.agrosewa

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.example.nafis.agrosewa.Data.UserDetail
import com.example.nafis.agrosewa.Data.communityPost
import com.example.nafis.agrosewa.databinding.ActivityPostBinding
import com.example.nafis.agrosewa.fragment.Community
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Post : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private var imgUri:Uri?=null
    private lateinit var formattedDate:String
    private lateinit var formattedTime:String
    private var lastPostId = 0


    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        imgUri=uri
        binding.selectPhoto.setImageURI(imgUri)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPostBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.posttoolbar.setNavigationOnClickListener { onBackPressed() }

        uploadPostdata()
    }

    private fun uploadPostdata(){


        binding.selectPhoto.setOnClickListener{
            selectImage.launch("image/*")
        }

        binding.postSubmit.setOnClickListener {
            val title = binding.postEditTitle.text.toString()
            val desc = binding.postEditDesc.text.toString()
            if (title.isNotEmpty() && desc.isNotEmpty() && imgUri != null) {
                uploadImage(title, desc)
            } else {
                progress.toastShow(this@Post, "Please fill in all fields and select an image")
            }
        }

    }

    private fun uploadImage(title: String, desc: String) {
          val currentUser=FirebaseAuth.getInstance().currentUser.toString()
          progress.show(this)
          lifecycleScope.launch {
              try {
                 val fs= FirebaseStorage.getInstance().getReference("CommunityPhoto").child(currentUser).child("plant.jpg")
                  val uploadTask=fs.putFile(imgUri!!).await()

                  if(uploadTask.task.isSuccessful){
                      val downloadUrl=fs.downloadUrl.await()
                      uploadPostDetail(title,desc,downloadUrl)
                      progress.hide()
                  }else{
                      progress.toastShow(this@Post,"image Upload fail")
                  }


              }catch (e:Exception){
                  progress.toastShow(this@Post,e.message.toString())
              }
          }
    }

    private fun uploadPostDetail(title: String, desc: String, postImg: Uri?) {
        getDatetime()
        val firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid ?: return

        val postId = generatePostId()  // Generate a valid post ID

        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.getValue(UserDetail::class.java)
                val name = data?.username
                val userImgUri = data?.userprofile

                val communityPost = communityPost(
                    postid = postId,  // Use the generated post ID
                    postuserid = uid,
                    postusername = name,
                    postuserimg = userImgUri,
                    posttitle = title,
                    postdesc = desc,
                    postdate = formattedDate,
                    posttime = formattedTime,
                    postimg = postImg.toString(),
                    postlike = 0,
                    postdislike = 0
                )

                val db = FirebaseDatabase.getInstance().getReference("CommunityPlantDetail").child(postId)
                db.setValue(communityPost).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        onBackPressed()
                        progress.toastShow(this@Post, "Upload Successful")
                    } else {
                        progress.toastShow(this@Post, "Upload Failed")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                progress.toastShow(this@Post, error.message)
            }
        })
    }


    fun getDatetime(){
        // Get current date and time
        val currentDateTime = Date()
        // Format the date and time
        val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        formattedDate = dateFormatter.format(currentDateTime)
        formattedTime = timeFormatter.format(currentDateTime)
    }

    private fun generatePostId(): String {
        return FirebaseDatabase.getInstance().reference.push().key ?: ""
    }


}