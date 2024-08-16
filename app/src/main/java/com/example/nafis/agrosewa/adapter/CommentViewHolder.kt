package com.example.nafis.agrosewa.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.DIffUserCallBack.CommentCallBack
import com.example.nafis.agrosewa.Data.Comment
import com.example.nafis.agrosewa.databinding.CommentLayoutBinding


class CommentViewHolder(
    val binding: CommentLayoutBinding,
    val callBack: CommentCallBack
):RecyclerView.ViewHolder(binding.root) {

    fun binddata(comment:Comment){
        binding.apply {
            Glide.with(itemView.context)
                .load(comment.userpro)
                .into(profileImageView)
            commentTextView.text=comment.commentdesc
            userNameTextView.text=comment.username
            timePostedTextView.text=comment.commenttime
        }

    }
}
