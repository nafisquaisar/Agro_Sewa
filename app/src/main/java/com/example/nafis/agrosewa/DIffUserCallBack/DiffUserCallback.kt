package com.example.nafis.agrosewa.DIffUserCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.nafis.agrosewa.Data.PlantData

object DiffUserCallback: DiffUtil.ItemCallback<PlantData>() {
    override fun areItemsTheSame(oldItem: PlantData, newItem: PlantData): Boolean {
        return oldItem.plantId==newItem.plantId
    }

    override fun areContentsTheSame(oldItem: PlantData, newItem: PlantData): Boolean {
        return oldItem.plantName==newItem.plantName&& oldItem.plantImg==newItem.plantImg
    }
}