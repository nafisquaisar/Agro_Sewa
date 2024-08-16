package com.example.nafis.agrosewa.ShopItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.DIffUserCallBack.UserItemCallback
import com.example.nafis.agrosewa.Data.PlantData
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.adapter.PlantDetailAdapter
import com.example.nafis.agrosewa.databinding.ActivityMorePlantBinding
import com.example.nafis.agrosewa.progress


class MorePlant : AppCompatActivity() {
    private lateinit var binding: ActivityMorePlantBinding

    private val userItemCallback by lazy {
        object :UserItemCallback{
            override fun onPlantClick(details: PlantData, position: Int) {
                progress.toastShow(this@MorePlant,"Plant Name ${details.plantName}")
            }

        }
    }

    private val plantAdapter by lazy { PlantDetailAdapter(userItemCallback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMorePlantBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.planttoolbar.setNavigationOnClickListener { onBackPressed() }

        fetchMorePlantData()

        binding.MorePlantRecyclerview.adapter=plantAdapter
        plantAdapter.submitList(fetchMorePlantData())
    }

    private fun fetchMorePlantData():ArrayList<PlantData> {

        val plant1=PlantData(1,"Money","https://m.media-amazon.com/images/I/61fswIwfuLL._AC_UF1000,1000_QL80_.jpg")
        val plant2=PlantData(2,"Mango","https://static.vecteezy.com/system/resources/previews/002/070/040/non_2x/yellow-mango-tree-isolated-on-white-background-free-vector.jpg")
        val plant3=PlantData(3,"Jade","https://5.imimg.com/data5/SG/DU/RJ/SELLER-10833874/jade-plant-in-round-dew.jpg")
        val plant4=PlantData(4,"Money","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant5=PlantData(5,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant6=PlantData(6,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant7=PlantData(5,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant8=PlantData(6,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")

        val plantdata= arrayListOf<PlantData>()

        plantdata.add(plant1)
        plantdata.add(plant2)
        plantdata.add(plant3)
        plantdata.add(plant4)
        plantdata.add(plant5)
        plantdata.add(plant6)
        plantdata.add(plant7)
        plantdata.add(plant8)

        return plantdata;
    }
}