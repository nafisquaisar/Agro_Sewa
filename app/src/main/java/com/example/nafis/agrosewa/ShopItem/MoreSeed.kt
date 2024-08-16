package com.example.nafis.agrosewa.ShopItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.DIffUserCallBack.MaterialItemCallback
import com.example.nafis.agrosewa.Data.PlantMaterial
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.adapter.MaterialAdapter
import com.example.nafis.agrosewa.databinding.ActivityMorePesticideBinding
import com.example.nafis.agrosewa.databinding.ActivityMoreSeedBinding
import com.example.nafis.agrosewa.progress

class MoreSeed : AppCompatActivity() {
    private lateinit var binding: ActivityMoreSeedBinding

    private val materialItemCallback by lazy {
        object : MaterialItemCallback {
            override fun materialOnClick(material: PlantMaterial, position: Int) {
                progress.toastShow(this@MoreSeed, "Material name is ${material.materialname}")
            }
        }
    }
    private val materialAdapter by lazy { MaterialAdapter(materialItemCallback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMoreSeedBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.seedstoolbar.setNavigationOnClickListener { onBackPressed() }
        binding.MoreSeedRecylerVIew.adapter=materialAdapter
        materialAdapter.submitList(getMaterialDetail())
    }


    private fun getMaterialDetail():ArrayList<PlantMaterial> {
        val mat1 = PlantMaterial(
            "1",
            "Bitter Gourd Seed",
            "1Kg",
            "Navika Seed",
            "₹93",
            "https://m.media-amazon.com/images/I/61mBfligwoL._SX679_.jpg",
            "https://www.amazon.in/Vegetable-Seeds-Gardening-Gardening-included/dp/B0CJ3FC2Y2/ref=sr_1_10?dib=eyJ2IjoiMSJ9.THe2FKzDQ1ObussgZbEt8tXDJ9I_uiVcAqwHT173M25bGucLktbqFHrYbyWCGJ8TUf6s_VJNgFjAtwfMELkNESE5zgkhggyftmsSQNdZIJ6y55vNVPxnNBAHMIfx9k-dH71XBfOUsTuV9d54ZrUluCBxlkov3eliceORpDEkbWpypPuvwD4fkoRJn0qAIVFETJLbjG-I55e8vaPnbtY4Y4V-CBEF883eEphmeLX5TNhqPPfalZwP0D8DqxL2qN4VuLQXELkQTDnBxB2jPb_rxlbRUfuAQnOlOyhpvjJIMzQ.UvejS7ksg-bLuVXVHMOEm3-2sZMSfrQmZYge8C3q9D0&dib_tag=se&keywords=Bitter%2BGourd%2BSeeds&qid=1717953592&sr=8-10&th=1"
        )
        val mat2 = PlantMaterial(
            "1",
            "Onion Seed",
            "1Kg",
            "Farmkart",
            "₹595",
            "https://m.media-amazon.com/images/I/61082vkt-FL.jpg",
            "https://www.amazon.in/dp/B0D4TZF3LW/ref=sspa_dk_detail_4?psc=1&pd_rd_i=B0D4TZF3LW&pd_rd_w=AH08Q&content-id=amzn1.sym.2575ab02-73ff-40ca-8d3a-4fbe87c5a28d&pf_rd_p=2575ab02-73ff-40ca-8d3a-4fbe87c5a28d&pf_rd_r=93XJZ2W09GECQEB5ZMNX&pd_rd_wg=AYwtp&pd_rd_r=e457cd30-ad60-47b4-9796-7391d7bd1585&s=garden&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWw"
        )

        val material= arrayListOf<PlantMaterial>()
        material.add(mat1)
        material.add(mat2)
//        material.add(mat3)
//        material.add(mat4)
//        material.add(mat5)
//        material.add(mat6)

        return material
    }

}