package com.example.nafis.agrosewa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.nafis.agrosewa.DIffUserCallBack.DiffUserCallback
import com.example.nafis.agrosewa.DIffUserCallBack.UserItemCallback
import com.example.nafis.agrosewa.Data.PlantData
import com.example.nafis.agrosewa.databinding.PlantCardviewBinding

class PlantDetailAdapter (val callback:UserItemCallback):ListAdapter<PlantData,PlantViewHolder>(DiffUserCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val binding=PlantCardviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PlantViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.bind(currentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}