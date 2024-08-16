package com.example.nafis.agrosewa.DIffUserCallBack

import com.example.nafis.agrosewa.Data.PlantData

interface UserItemCallback {
    fun onPlantClick(details:PlantData,position: Int)
}