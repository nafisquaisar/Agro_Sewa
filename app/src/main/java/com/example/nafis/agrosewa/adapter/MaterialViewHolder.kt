package com.example.nafis.agrosewa.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.DIffUserCallBack.MaterialItemCallback
import com.example.nafis.agrosewa.Data.PlantData
import com.example.nafis.agrosewa.Data.PlantMaterial
import com.example.nafis.agrosewa.databinding.PlantMaterialBinding

class MaterialViewHolder (
    val binding: PlantMaterialBinding,
    val callback: MaterialItemCallback
) :RecyclerView.ViewHolder(binding.root){
    fun binddata(data:PlantMaterial){
        binding.apply {
            Glide.with(itemView.context)
                .load(data.materialimg)
                .into(materialimg)
            materialName.text=data.materialname
            materialCompany.text=data.materialcompany
            materialPrice.text=data.materialprice
            materialWeight.text=data.materialweight
            itemView.setOnClickListener {
                callback.materialOnClick(data,position)
            }
        }
    }
}