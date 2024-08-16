package com.example.nafis.agrosewa.DIffUserCallBack

import androidx.recyclerview.widget.DiffUtil
import com.example.nafis.agrosewa.Data.Shopdetial

object DiffShopCallback : DiffUtil.ItemCallback<Shopdetial>() {
    override fun areItemsTheSame(oldItem: Shopdetial, newItem: Shopdetial): Boolean {
        return oldItem.shopid==newItem.shopid
    }

    override fun areContentsTheSame(oldItem: Shopdetial, newItem: Shopdetial): Boolean {
        return oldItem.shopcity==newItem.shopcity
                && oldItem.shopdistance==newItem.shopdistance
                && oldItem.shopname==newItem.shopname
                && oldItem.shopimg==newItem.shopimg
                && oldItem.shopurl==newItem.shopurl
    }
}