package com.example.nafis.agrosewa.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.nafis.agrosewa.DIffUserCallBack.CommunityCallBack
import com.example.nafis.agrosewa.Data.communityPost
import com.example.nafis.agrosewa.Post
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.adapter.PostAdapter
import com.example.nafis.agrosewa.databinding.FragmentCommunityBinding
import com.example.nafis.agrosewa.progress
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

//https://png.pngtree.com/thumb_back/fh260/background/20220627/pngtree-colorado-beetle-larva-damages-potato-plants-a-dangerous-pest-photo-image_47320908.jpg
class Community : Fragment() {
    private lateinit var binding: FragmentCommunityBinding
    private val postCallback by lazy {
        object :CommunityCallBack{
            override fun onPostClcik(communityPost: communityPost, position: Int) {
                progress.toastShow(requireContext(),"Name is {${communityPost.postusername}}")
            }
        }
    }
    private val postAdapter by lazy { PostAdapter(postCallback) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentCommunityBinding.inflate(LayoutInflater.from(requireContext()))

        binding.communityRecyclerView.adapter = postAdapter
        fetchData()

        binding.Postbtn.setOnClickListener { startActivity(Intent(requireContext(),Post::class.java)) }

        return binding.root
    }

    private fun fetchData() {
        lifecycleScope.launch {
            try {
                val db = FirebaseDatabase.getInstance().getReference("CommunityPlantDetail")
                db.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val postList = ArrayList<communityPost>()
                        for (postSnapshot in snapshot.children) {
                            val post = postSnapshot.getValue(communityPost::class.java)
                            if (post != null) {
                                postList.add(post)
                            }
                        }
                        postList.reverse() // Reverse the order of posts
                        postAdapter.submitList(postList)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        progress.toastShow(requireContext(), error.message)
                    }
                })
            } catch (e: Exception) {
                progress.toastShow(requireContext(), e.message.toString())
            }
        }
    }


}