package com.example.nafis.agrosewa.adapter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.DIffUserCallBack.CommunityCallBack
import com.example.nafis.agrosewa.Data.communityPost
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.databinding.CommunityLayoutBinding
import com.example.nafis.agrosewa.progress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.OnItemClickListener
import com.orhanobut.dialogplus.ViewHolder


class PostViewHolder(
    private val binding: CommunityLayoutBinding,
    private val callback: CommunityCallBack
) : RecyclerView.ViewHolder(binding.root) {

    private val database = FirebaseDatabase.getInstance().getReference("CommunityPlantDetail")
    private val likesDatabase = FirebaseDatabase.getInstance().getReference("Communitylikes")
    private val dislikesDatabase = FirebaseDatabase.getInstance().getReference("Communitydislikes")
    private val auth = FirebaseAuth.getInstance()
    private var isDeletebtnVis=false

    fun databind(post: communityPost) {
        binding.apply {
            Glide.with(itemView.context)
                .load(post.postimg)
                .into(defecttedImgUpload)

            if (post.postuserimg.isNullOrEmpty()) {
                communityProfile.setImageResource(R.drawable.profile)
            } else {
                Glide.with(itemView.context)
                    .load(post.postuserimg)
                    .into(communityProfile)
            }

            communityTitle.text = post.posttitle
            communityDesc.text = post.postdesc
            communityName.text = post.postusername
            communityDate.text = post.postdate
            communitytime.text = post.posttime
            coummunitylikeview.text = "${post.postlike} likes"
            coummunitydislikeview.text = "${post.postdislike} dislikes"

            val userId = auth.currentUser?.uid
            if (userId != null) {
                // Check if the user has already liked the post
                likesDatabase.child(post.postid).child(userId).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        coummunitylikebtn.setImageResource(R.drawable.afterlike)
                    } else {
                        coummunitylikebtn.setImageResource(R.drawable.beforelike)
                    }
                }

                // Check if the user has already disliked the post
                dislikesDatabase.child(post.postid).child(userId).get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        coummunitydislikebtn.setImageResource(R.drawable.afterdislike)
                    } else {
                        coummunitydislikebtn.setImageResource(R.drawable.beforedislike)
                    }
                }
            }

            coummunitylikebtn.setOnClickListener {
                handleLikeButtonClick(post)
            }

            coummunitydislikebtn.setOnClickListener {
                handleDislikeButtonClick(post)
            }
            deleteoptionsMenu.setOnClickListener {
                toggleDeleteButtonVisibility()
            }
            // delete post
            communitydeletevtn.setOnClickListener {
                deletePost(post)
            }
            binding.commentbtn.setOnClickListener {
                val dialog = DialogPlus.newDialog(itemView.context)
                    .setContentHolder(ViewHolder(R.layout.activity_comment_system))
                    .setOnItemClickListener { dialog, item, view, position ->
                        // Handle comment item click
                    }
                    .setExpanded(true)
                    .create()
                dialog.show()
            }

        }
    }

    private fun toggleDeleteButtonVisibility() {
        if (!isDeletebtnVis) {
            binding.communitydeletevtn.visibility = View.VISIBLE
            isDeletebtnVis = true
        } else {
            binding.communitydeletevtn.visibility = View.GONE
            isDeletebtnVis = false
        }
    }
    private fun handleLikeButtonClick(post: communityPost) {
        val userId = auth.currentUser?.uid ?: return
        val likeRef = likesDatabase.child(post.postid).child(userId)
        val dislikeRef = dislikesDatabase.child(post.postid).child(userId)

        likeRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                likeRef.removeValue().addOnSuccessListener {
                    updateLikeCount(post, -1)
                    binding.coummunitylikebtn.setImageResource(R.drawable.beforelike)
                }
            } else {
                likeRef.setValue(true).addOnSuccessListener {
                    updateLikeCount(post, 1)
                    binding.coummunitylikebtn.setImageResource(R.drawable.afterlike)
                }

                // If the user has previously disliked the post, remove the dislike
                dislikeRef.get().addOnSuccessListener { dislikeSnapshot ->
                    if (dislikeSnapshot.exists()) {
                        dislikeRef.removeValue().addOnSuccessListener {
                            updateDislikeCount(post, -1)
                            binding.coummunitydislikebtn.setImageResource(R.drawable.beforedislike)
                        }
                    }
                }
            }
        }
    }

    private fun handleDislikeButtonClick(post: communityPost) {
        val userId = auth.currentUser?.uid ?: return
        val dislikeRef = dislikesDatabase.child(post.postid).child(userId)
        val likeRef = likesDatabase.child(post.postid).child(userId)

        dislikeRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                dislikeRef.removeValue().addOnSuccessListener {
                    updateDislikeCount(post, -1)
                    binding.coummunitydislikebtn.setImageResource(R.drawable.beforedislike)
                }
            } else {
                dislikeRef.setValue(true).addOnSuccessListener {
                    updateDislikeCount(post, 1)
                    binding.coummunitydislikebtn.setImageResource(R.drawable.afterdislike)
                }

                // If the user has previously liked the post, remove the like
                likeRef.get().addOnSuccessListener { likeSnapshot ->
                    if (likeSnapshot.exists()) {
                        likeRef.removeValue().addOnSuccessListener {
                            updateLikeCount(post, -1)
                            binding.coummunitylikebtn.setImageResource(R.drawable.beforelike)
                        }
                    }
                }
            }
        }
    }

    private fun updateLikeCount(post: communityPost, count: Int) {
        val postRef = database.child(post.postid)
        postRef.child("postlike").get().addOnSuccessListener {
            val currentCount = it.getValue(Int::class.java) ?: 0
            val newCount = currentCount + count
            postRef.child("postlike").setValue(newCount).addOnSuccessListener {
                binding.coummunitylikeview.text = "$newCount likes"
            }
        }
    }

    private fun updateDislikeCount(post: communityPost, count: Int) {
        val postRef = database.child(post.postid)
        postRef.child("postdislike").get().addOnSuccessListener {
            val currentCount = it.getValue(Int::class.java) ?: 0
            val newCount = currentCount + count
            postRef.child("postdislike").setValue(newCount).addOnSuccessListener {
                binding.coummunitydislikeview.text = "$newCount dislikes"
            }
        }
    }

    // delete operation
    private fun deletePost(post: communityPost) {
        val postRef = database.child(post.postid)
        postRef.removeValue().addOnSuccessListener {
           progress.toastShow(itemView.context,"Delete Successfully")
        }.addOnFailureListener { exception ->
            // Handle failure to delete post
            Log.e(TAG, "Failed to delete post with id ${post.postid}", exception)
        }
    }

}
