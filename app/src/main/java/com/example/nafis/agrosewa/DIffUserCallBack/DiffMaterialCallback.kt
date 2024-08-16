package com.example.nafis.agrosewa.DIffUserCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.nafis.agrosewa.Data.PlantMaterial

object DiffMaterialCallback :DiffUtil.ItemCallback<PlantMaterial>() {
    override fun areItemsTheSame(oldItem: PlantMaterial, newItem: PlantMaterial): Boolean {
        return oldItem.materialid==newItem.materialid
    }

    override fun areContentsTheSame(oldItem: PlantMaterial, newItem: PlantMaterial): Boolean {
        return oldItem.materialname==newItem.materialname &&
                oldItem.materialcompany==newItem.materialcompany
                && oldItem.materialweight==newItem.materialweight
                && oldItem.materialimg==newItem.materialimg &&
                oldItem.materialprice==newItem.materialprice
    }
}