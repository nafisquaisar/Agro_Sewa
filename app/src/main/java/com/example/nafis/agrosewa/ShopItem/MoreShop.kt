package com.example.nafis.agrosewa.ShopItem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.nafis.agrosewa.DIffUserCallBack.ShopItemCallback
import com.example.nafis.agrosewa.Data.Shopdetial
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.adapter.ShopAdapter
import com.example.nafis.agrosewa.databinding.ActivityMoreShopBinding
import com.example.nafis.agrosewa.progress

class MoreShop : AppCompatActivity() {
    private lateinit var binding: ActivityMoreShopBinding

    private val shopItemCallback by lazy {
        object : ShopItemCallback {
            override fun onShopClick(shopdetial: Shopdetial, position: Int) {
                progress.toastShow(this@MoreShop,"Shop name is ${shopdetial.shopname}")
            }
        }
    }
    private val shopAdapter by lazy { ShopAdapter(shopItemCallback) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMoreShopBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.shoptoolbar.setNavigationOnClickListener { onBackPressed() }
        binding.MoreShopRecylerVIew.adapter=shopAdapter
        shopAdapter.submitList(getShopDetail())
    }

    private fun getShopDetail():ArrayList<Shopdetial> {

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
        val shop=arrayListOf<Shopdetial>()

        shop.add(shop1)
        shop.add(shop2)

        return shop
    }
}