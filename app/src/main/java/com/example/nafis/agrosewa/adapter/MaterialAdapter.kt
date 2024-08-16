package com.example.nafis.agrosewa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.nafis.agrosewa.DIffUserCallBack.DiffMaterialCallback
import com.example.nafis.agrosewa.DIffUserCallBack.MaterialItemCallback
import com.example.nafis.agrosewa.Data.PlantMaterial
import com.example.nafis.agrosewa.databinding.PlantMaterialBinding

class MaterialAdapter (private val callback: MaterialItemCallback): ListAdapter<PlantMaterial, MaterialViewHolder>(DiffMaterialCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialViewHolder {
        val binding=PlantMaterialBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MaterialViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: MaterialViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.binddata(currentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}