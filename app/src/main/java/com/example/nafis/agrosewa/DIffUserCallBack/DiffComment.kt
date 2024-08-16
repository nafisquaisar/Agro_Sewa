package com.example.nafis.agrosewa.DIffUserCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.nafis.agrosewa.Data.Comment

object DiffComment:DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentid==newItem.commentid
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
        return oldItem.commentdesc==newItem.commentdesc&&
                oldItem.commentlike==newItem.commentlike&&
                oldItem.commenttime==newItem.commenttime&&
                oldItem.commentdislike==newItem.commentdislike&&
                oldItem.userpro==newItem.userpro&&
                oldItem.username==newItem.username
    }
}