package com.example.nafis.agrosewa.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.nafis.agrosewa.DIffUserCallBack.DiffShopCallback
import com.example.nafis.agrosewa.DIffUserCallBack.ShopItemCallback
import com.example.nafis.agrosewa.Data.Shopdetial
import com.example.nafis.agrosewa.databinding.ShopLayoutBinding

class ShopAdapter(val callback: ShopItemCallback):ListAdapter<Shopdetial,ShopViewHolder>(DiffShopCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopViewHolder {
        val binding=ShopLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ShopViewHolder(binding,callback)
    }

    override fun onBindViewHolder(holder: ShopViewHolder, position: Int) {
        val currentItem=getItem(position)
        holder.databind(currentItem)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}