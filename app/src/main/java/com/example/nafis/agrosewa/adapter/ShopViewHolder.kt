package com.example.nafis.agrosewa.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nafis.agrosewa.DIffUserCallBack.ShopItemCallback
import com.example.nafis.agrosewa.Data.Shopdetial
import com.example.nafis.agrosewa.databinding.ShopLayoutBinding

class ShopViewHolder (
    val binding: ShopLayoutBinding,
    val callback: ShopItemCallback
) :RecyclerView.ViewHolder(binding.root){

    fun databind(shopdetial: Shopdetial){
        binding.apply {
            Glide.with(itemView.context)
                .load(shopdetial.shopimg)
                .into(shopimg)
            shopname.text=shopdetial.shopname
            shopdistance.text=shopdetial.shopdistance
            shopcity.text=shopdetial.shopcity
            itemView.setOnClickListener {
                callback.onShopClick(shopdetial,position)
            }
        }
    }
}