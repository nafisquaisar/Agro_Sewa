package com.example.nafis.agrosewa.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nafis.agrosewa.DIffUserCallBack.MaterialItemCallback
import com.example.nafis.agrosewa.DIffUserCallBack.ShopItemCallback
import com.example.nafis.agrosewa.Data.PlantMaterial
import com.example.nafis.agrosewa.Data.Shopdetial
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.ShopItem.MorePesticide
import com.example.nafis.agrosewa.ShopItem.MoreSeed
import com.example.nafis.agrosewa.ShopItem.MoreShop
import com.example.nafis.agrosewa.adapter.MaterialAdapter
import com.example.nafis.agrosewa.adapter.ShopAdapter
import com.example.nafis.agrosewa.databinding.FragmentAgroShopBinding
import com.example.nafis.agrosewa.progress

class AgroShop : Fragment() {
    private lateinit var binding: FragmentAgroShopBinding

    private val shopItemCallback by lazy {
        object : ShopItemCallback {
            override fun onShopClick(shopdetial: Shopdetial, position: Int) {
                progress.toastShow(requireContext(), "Shop name is ${shopdetial.shopname}")
                val shopurl=shopdetial.shopurl
                val intent=Intent(Intent.ACTION_VIEW, Uri.parse(shopurl))
                startActivity(intent)
            }
        }
    }
    private val seedCallback by lazy {
        object : MaterialItemCallback {
            override fun materialOnClick(material: PlantMaterial, position: Int) {
                progress.toastShow(requireContext(), "Material name is ${material.materialname}")
                val maturl=material.materialurl
                val intent=Intent(Intent.ACTION_VIEW, Uri.parse(maturl))
                startActivity(intent)
            }
        }
    }

    private val pesticideCallback by lazy {
        object : MaterialItemCallback {
            override fun materialOnClick(material: PlantMaterial, position: Int) {
                progress.toastShow(requireContext(), "Material name is ${material.materialname}")
                val maturl=material.materialurl
                val intent=Intent(Intent.ACTION_VIEW, Uri.parse(maturl))
                startActivity(intent)
            }
        }
    }

    private val shopAdapter by lazy { ShopAdapter(shopItemCallback) }
    private val seedAdapter by lazy { MaterialAdapter(seedCallback) }
    private val pesticideAdapter by lazy { MaterialAdapter(pesticideCallback) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgroShopBinding.inflate(LayoutInflater.from(requireContext()))


        binding.shoprecyclerview.adapter = shopAdapter
        shopAdapter.submitList(getShopDetail())

        binding.seedRecyclerView.adapter = seedAdapter
        seedAdapter.submitList(getSeedDetail())

        binding.pesticideRecyclerView.adapter = pesticideAdapter
        pesticideAdapter.submitList(getPesticideDetail())

        binding.apply {
            allshop.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        MoreShop::class.java
                    )
                )
            }
            allseed.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        MoreSeed::class.java
                    )
                )
            }
            allpesticide.setOnClickListener {
                startActivity(
                    Intent(
                        requireContext(),
                        MorePesticide::class.java
                    )
                )
            }
        }

        return binding.root
    }

    private fun getSeedDetail(): ArrayList<PlantMaterial> {
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
            "2",
            "Onion Seed",
            "1Kg",
            "Farmkart",
            "₹595",
            "https://m.media-amazon.com/images/I/61082vkt-FL.jpg",
            "https://www.amazon.in/dp/B0D4TZF3LW/ref=sspa_dk_detail_4?psc=1&pd_rd_i=B0D4TZF3LW&pd_rd_w=AH08Q&content-id=amzn1.sym.2575ab02-73ff-40ca-8d3a-4fbe87c5a28d&pf_rd_p=2575ab02-73ff-40ca-8d3a-4fbe87c5a28d&pf_rd_r=93XJZ2W09GECQEB5ZMNX&pd_rd_wg=AYwtp&pd_rd_r=e457cd30-ad60-47b4-9796-7391d7bd1585&s=garden&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWw"
        )

        val seed = arrayListOf<PlantMaterial>()
        seed.add(mat1)
        seed.add(mat2)

        return seed
    }


    private fun getPesticideDetail(): ArrayList<PlantMaterial> {
        val mat1 = PlantMaterial(
            "3",
            "Pure Neem Oil",
            "200ml",
            "Chipku",
            "₹247",
            "https://m.media-amazon.com/images/I/513-JQQtDwL._SY445_SX342_QL70_FMwebp_.jpg",
             "https://www.amazon.in/Natural-Organic-Pressed-Plants-Measuring/dp/B075THM1T3/ref=zg_bs_g_26381366031_d_sccl_4/261-7839363-3374120?th=1"
        )
        val mat2 = PlantMaterial(
            "4",
            "Neem Oil",
            "250ml",
            "Pot And Bloom",
            "₹278",
            "https://m.media-amazon.com/images/I/71mGi3MMD8L._SX522_.jpg",
            "https://www.amazon.in/dp/B0BPLQ2L13/ref=sspa_dk_detail_1?pd_rd_i=B0BPLQ2L13&pd_rd_w=Zc0TH&content-id=amzn1.sym.0fcdb56a-738b-4621-9da7-d47193883987&pf_rd_p=0fcdb56a-738b-4621-9da7-d47193883987&pf_rd_r=P0X53MBR2BRFPKQTQZNW&pd_rd_wg=sPCmA&pd_rd_r=9a4be12e-4a73-46af-ba28-b462a7314817&s=garden&sp_csd=d2lkZ2V0TmFtZT1zcF9kZXRhaWwy&th=1"
        )

        val material = arrayListOf<PlantMaterial>()
        material.add(mat1)
        material.add(mat2)

        return material
    }

    private fun getShopDetail(): ArrayList<Shopdetial> {

        val shop1 = Shopdetial(
            "1",
            "Krishi Mitra",
            "Bhopal",
            "2 Km Away",
            "https://content.jdmagicbox.com/comp/bhopal/c1/0755px755.x755.140507132917.v9c1/catalogue/chanchal-kirana-and-general-store-chola-road-bhopal-general-stores-1dng3pl.jpg",
             "https://maps.app.goo.gl/wtRWDPwbjMY3T5Kq5"
        )
        val shop2 = Shopdetial(
            "2",
            "Bhopal Agro sell",
            "Bhopal",
            "4 Km Away",
            "https://content.jdmagicbox.com/comp/bhopal/j5/0755px755.x755.170603150039.d6j5/catalogue/max-super-market-indrapuri-bhopal-general-stores-ur99lwmvt9.jpg",
            "https://maps.app.goo.gl/kRaX2AV7TFsiBAbC9"
        )
        val shop = arrayListOf<Shopdetial>()

        shop.add(shop1)
        shop.add(shop2)

        return shop
    }
}