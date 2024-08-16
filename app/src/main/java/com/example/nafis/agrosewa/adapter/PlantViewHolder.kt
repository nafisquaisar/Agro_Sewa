package com.example.nafis.agrosewa.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.DIffUserCallBack.UserItemCallback
import com.example.nafis.agrosewa.Data.PlantData
import com.example.nafis.agrosewa.databinding.PlantCardviewBinding

class PlantViewHolder(
    val binding:PlantCardviewBinding,
    val callback:UserItemCallback
):RecyclerView.ViewHolder(binding.root) {

    fun bind(data:PlantData){
        binding.apply {
            Glide.with(itemView.context)
                .load(data.plantImg)
                .into(plantCardView)
            plantCardName.text=data.plantName
            itemView.setOnClickListener {
                callback.onPlantClick(data, position)
            }
        }
    }
}
