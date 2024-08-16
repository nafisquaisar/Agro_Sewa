package com.example.nafis.agrosewa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.nafis.agrosewa.DIffUserCallBack.CommunityCallBack
import com.example.nafis.agrosewa.DIffUserCallBack.DiffCommunityCallback
import com.example.nafis.agrosewa.Data.communityPost
import com.example.nafis.agrosewa.databinding.CommunityLayoutBinding

class PostAdapter (val callback:CommunityCallBack):ListAdapter<communityPost,PostViewHolder>(DiffCommunityCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding=CommunityLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPos=getItem(position)
        holder.databind(currentPos)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}