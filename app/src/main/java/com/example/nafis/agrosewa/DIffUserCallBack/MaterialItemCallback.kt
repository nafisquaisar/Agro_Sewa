package com.example.nafis.agrosewa.DIffUserCallBack

import com.example.nafis.agrosewa.Data.PlantMaterial

interface MaterialItemCallback {
    fun materialOnClick(material: PlantMaterial,position:Int)
}