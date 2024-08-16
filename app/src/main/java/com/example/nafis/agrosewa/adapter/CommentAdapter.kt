package com.example.nafis.agrosewa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.nafis.agrosewa.DIffUserCallBack.CommentCallBack
import com.example.nafis.agrosewa.DIffUserCallBack.DiffComment
import com.example.nafis.agrosewa.Data.Comment
import com.example.nafis.agrosewa.databinding.CommentLayoutBinding

class CommentAdapter(val callback:CommentCallBack):ListAdapter<Comment,CommentViewHolder>(DiffComment) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding=CommentLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CommentViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}