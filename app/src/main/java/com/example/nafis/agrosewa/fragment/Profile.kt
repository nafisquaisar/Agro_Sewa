package com.example.nafis.agrosewa.fragment

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.Chatbot
import com.example.nafis.agrosewa.Data.UserDetail
import com.example.nafis.agrosewa.Helpcare
import com.example.nafis.agrosewa.Login
import com.example.nafis.agrosewa.Notification
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.databinding.FragmentProfileBinding
import com.example.nafis.agrosewa.progress
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class Profile : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var imgUri: Uri? = null
    private var editPic: CircleImageView? = null
    private lateinit var selectImageLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectImageLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                imgUri = uri
                editPic?.setImageURI(imgUri)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(LayoutInflater.from(context))

        binding.profilelogout.setOnClickListener {
            logoutprocess()
        }
        binding.profilechatbot.setOnClickListener {
            startActivity(Intent(requireContext(), Chatbot::class.java))

        }
        binding.profilenotification.setOnClickListener {
            startActivity(Intent(requireContext(), Notification::class.java))

        }
        binding.helpcare.setOnClickListener {
            startActivity(Intent(requireContext(), Helpcare::class.java))
        }


        binding.profileeditbtn.setOnClickListener {
            editProfileProcess()
        }
        setAlldetail()

        return binding.root
    }

    private fun setAlldetail() {

        val database = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val myRef = database.getReference("Users").child(uid)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(UserDetail::class.java)
                val imgUri = value?.userprofile
                val username = value?.username
                val email = value?.useremail
                binding.profileNameset.text = username
                binding.profileEmailset.text = email
                val img = binding.profilepicset

                if (!imgUri.isNullOrEmpty()) {
                    try {
                        // Use Glide to load the image from the URL
                        if (imgUri.isEmpty()) {
                            img.setImageResource(R.drawable.profile)
                        }else {
                            Glide.with(requireContext())
                                .load(imgUri)
                                .into(img)
                            progress.hide()
                            Log.d("Profile", "Image URI set: $imgUri")
                        }
                    } catch (e: Exception) {
                        progress.hide()
                        Log.e("Profile", "Error setting image URI: ${e.message}")
                    }
                } else {
                    progress.hide()
                    Log.d("Profile", "Image URI is null or empty")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                progress.hide()
                println("Failed to read value: ${error.toException()}")
            }
        })
    }

    private fun editProfileProcess() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.editprofilelayout)
        dialog.show()

        val editCancelBtn = dialog.findViewById<Button>(R.id.editCancelbtn)
        val updateBtn = dialog.findViewById<Button>(R.id.editUpdatebtn)
        editPic = dialog.findViewById(R.id.editpropic)

        editPic?.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }

        editCancelBtn.setOnClickListener { dialog.dismiss() }

        val nameInputLayout = dialog.findViewById<TextInputLayout>(R.id.EdtiName)
        val numberInputLayout = dialog.findViewById<TextInputLayout>(R.id.EditNumber)

        val na = nameInputLayout.editText // Access the EditText within the TextInputLayout
        val nu = numberInputLayout.editText // Access the EditText within the TextInputLayout

        val database = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val myRef = database.getReference("Users").child(uid)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(UserDetail::class.java)
                val imagegUri = value?.userprofile
                val username = value?.username
                val num = value?.usernumber
                na?.setText(username ?: "")
                nu?.setText(num ?: "")

                if (!imagegUri.isNullOrEmpty()) {
                    try {
                        // Use Glide to load the image from the URL
                        Glide.with(requireContext())
                            .load(imagegUri)
                            .into(editPic!!)
                    } catch (e: Exception) {
                        progress.toastShow(requireContext(), e.message.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                println("Failed to read value: ${error.toException()}")
            }
        })

        updateBtn.setOnClickListener {
            progress.show(requireContext())
            val name = na?.text.toString()
            val number = nu?.text.toString()

            if (name.isEmpty() || number.isEmpty()) {
                progress.toastShow(requireContext(), "Name and Number cannot be empty")
                return@setOnClickListener
            }

            if (imgUri != null) {
                imageUpload(name, number)
            } else {
                // Use existing image URL
                val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
                val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser)
                databaseRef.child("userprofile").get().addOnSuccessListener {
                    val existingImageUrl = it.value.toString()
                    updatedata(name, number, Uri.parse(existingImageUrl))
                }.addOnFailureListener {
                    progress.toastShow(requireContext(), "Failed to get existing image URL")
                }
            }
            dialog.dismiss()
        }
    }


    private fun imageUpload(name: String, number: String) {

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storageRefrene =
            FirebaseStorage.getInstance().getReference("UserProfile").child(currentUser)
                .child("Profile.jpg")

        lifecycleScope.launch {
            try {
                val uploadTask = storageRefrene.putFile(imgUri!!).await()
                if (uploadTask.task.isSuccessful) {

                    val downloadUrl = storageRefrene.downloadUrl.await()
                    updatedata(name, number, downloadUrl)
                }
            } catch (e: Exception) {
                progress.hide()
                progress.toastShow(requireContext(), e.message.toString())
            }
        }
    }

    private fun updatedata(name: String, number: String, downloadUrl: Uri?) {

        lifecycleScope.launch {
            try {
                val firebaseAuth = FirebaseAuth.getInstance()
                val uid = firebaseAuth.currentUser?.uid.toString()
                val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)

                // Use a single event listener to avoid multiple calls
                databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val data = snapshot.getValue(UserDetail::class.java)
                        val email = data?.useremail
                        val password = data?.userpassword
                        val userDetail = UserDetail(
                            uid,
                            email,
                            number,
                            password,
                            name,
                            downloadUrl.toString()
                        )
                        databaseRef.setValue(userDetail).addOnCompleteListener {
                            if (it.isSuccessful) {
                                progress.hide()
                                progress.toastShow(requireContext(), "Update Successful")
                            } else {
                                progress.toastShow(requireContext(), "Update Failed")
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        progress.hide()
                        progress.toastShow(requireContext(), error.message)
                    }
                })
            } catch (e: Exception) {
                progress.hide()
                progress.toastShow(requireContext(), e.message.toString())
            }
        }
    }


    private fun logoutprocess() {
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Are you sure you want to logout")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(), Login::class.java))
                activity?.finish();
            }
            .setNegativeButton("No") { _, _ ->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }


}