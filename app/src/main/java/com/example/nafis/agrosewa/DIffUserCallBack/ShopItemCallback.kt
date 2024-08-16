package com.example.nafis.agrosewa.DIffUserCallBack

import com.example.nafis.agrosewa.Data.Shopdetial
import java.text.FieldPosition

interface ShopItemCallback {
    fun onShopClick(shopdetial: Shopdetial,position:Int)
}