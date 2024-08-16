package com.example.nafis.agrosewa.ShopItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.DIffUserCallBack.MaterialItemCallback
import com.example.nafis.agrosewa.DIffUserCallBack.ShopItemCallback
import com.example.nafis.agrosewa.Data.PlantMaterial
import com.example.nafis.agrosewa.Data.Shopdetial
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.adapter.MaterialAdapter
import com.example.nafis.agrosewa.adapter.ShopAdapter
import com.example.nafis.agrosewa.databinding.ActivityMorePesticideBinding
import com.example.nafis.agrosewa.progress

class MorePesticide : AppCompatActivity() {
    private lateinit var binding: ActivityMorePesticideBinding

    private val materialItemCallback by lazy {
        object : MaterialItemCallback {
            override fun materialOnClick(material: PlantMaterial, position: Int) {
                progress.toastShow(this@MorePesticide, "Material name is ${material.materialname}")
            }
        }
    }

    private val materialAdapter by lazy { MaterialAdapter(materialItemCallback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMorePesticideBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.pesticidetoolbar.setNavigationOnClickListener { onBackPressed() }
        binding.MorePesticideRecylerVIew.adapter=materialAdapter
        materialAdapter.submitList(getMaterialDetail())

    }
    private fun getMaterialDetail():ArrayList<PlantMaterial> {
        val mat1 = PlantMaterial(
            "1",
            "Pure Neem Oil",
            "200ml",
            "Chipku",
            "₹247",
            "https://m.media-amazon.com/images/I/513-JQQtDwL._SY445_SX342_QL70_FMwebp_.jpg",
            "https://www.amazon.in/Natural-Organic-Pressed-Plants-Measuring/dp/B075THM1T3/ref=zg_bs_g_26381366031_d_sccl_4/261-7839363-3374120?th=1"
        )
        val mat2 = PlantMaterial(
            "2",
            "Neem Oil",
            "250ml",
            "Pot And Bloom",
            "₹278",
            "https://m.media-amazon.com/images/I/71mGi3MMD8L._SX522_.jpg",
            "https://www.amazon.in/dp/B0BPLQ2L13/ref=sspa_dk_detail_1?pd_rd_i=B0BPLQ2L13&pd_rd_w=Zc0TH&content-id=amzn1.sym.0fcdb56a-738b-4621-9da7-d47193883987&pf_rd_p=0fcdb56a-738b-4621-9da7-d47193883987&pf_rd_r=P0X53MBR2BRFPKQTQZNW&pd_rd_wg=sPCmA&pd_rd_r=9a4be12e-4a73-46af-ba28-b462a7314817&s=garden&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWwy&th=1"
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