package com.example.nafis.agrosewa.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.nafis.agrosewa.Apiweather.ApiInterface
import com.example.nafis.agrosewa.DIffUserCallBack.UserItemCallback
import com.example.nafis.agrosewa.Data.PlantData
import com.example.nafis.agrosewa.R
import com.example.nafis.agrosewa.ShopItem.MorePlant
import com.example.nafis.agrosewa.adapter.PlantDetailAdapter
import com.example.nafis.agrosewa.databinding.FragmentMyplantBinding
import com.example.nafis.agrosewa.progress
import com.example.nafis.agrosewa.weather.weatherapp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//api 7ea34cb038b658fc9d5668e14ebc5bab
class myplant : Fragment() {
     private lateinit var binding: FragmentMyplantBinding

     private val userItemCallback by lazy {
         object :UserItemCallback{
             override fun onPlantClick(details: PlantData, position: Int) {
                 progress.toastShow(requireContext(),"Plant Name ${details.plantName}")
             }

         }
     }

    private val plantAdapter by lazy { PlantDetailAdapter(userItemCallback) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentMyplantBinding.inflate(LayoutInflater.from(context))
        fetchWeatheData("Bhopal")

        getData()
        binding.PlantrecyclerView.adapter=plantAdapter
        plantAdapter.submitList(getData())

        binding.MorePlant.setOnClickListener { startActivity(Intent(requireContext(),MorePlant::class.java)) }

        return binding.root
    }

    private fun getData():ArrayList<PlantData> {
        val plant1=PlantData(1,"Money","https://m.media-amazon.com/images/I/61fswIwfuLL._AC_UF1000,1000_QL80_.jpg")
        val plant2=PlantData(2,"Mango","https://static.vecteezy.com/system/resources/previews/002/070/040/non_2x/yellow-mango-tree-isolated-on-white-background-free-vector.jpg")
        val plant3=PlantData(3,"Jade","https://5.imimg.com/data5/SG/DU/RJ/SELLER-10833874/jade-plant-in-round-dew.jpg")
        val plant4=PlantData(4,"Money","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant5=PlantData(5,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")
        val plant6=PlantData(6,"Tomato","https://i1.fnp.com/images/pr/l/v20210405172754/money-plant-in-orange-powder-coated-metal-pot_1.jpg")

        val plantsArray= arrayListOf<PlantData>()

        plantsArray.add(plant1)
        plantsArray.add(plant2)
//        plantsArray.add(plant3)
//        plantsArray.add(plant4)
//        plantsArray.add(plant5)
//        plantsArray.add(plant6)

        return plantsArray
    }

    private fun fetchWeatheData(city: String) {
      val retrofit=Retrofit.Builder()
          .addConverterFactory(GsonConverterFactory.create())
          .baseUrl("https://api.openweathermap.org/data/2.5/")
          .build().create(ApiInterface::class.java)


   val response=retrofit.getWeatherData(city,"7ea34cb038b658fc9d5668e14ebc5bab","metric")
        response.enqueue(object :Callback<weatherapp>{
            override fun onResponse(call: Call<weatherapp>, response: Response<weatherapp>) {
                val responseBody=response.body()
                if(response.isSuccessful && responseBody!=null){

                 val temp=responseBody.main.temp.toInt()
                 val feellike=responseBody.main.feels_like.toInt()
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"

                    binding.cityname.text=city
                    binding.feellike.text="feel like a $feellike°C"
                    binding.temp.text="$temp°C"

                    changeImageAccoudingToCondition(condition)

                }
            }

            override fun onFailure(call: Call<weatherapp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun changeImageAccoudingToCondition(condition: String) {
        when(condition){
            "Clear Sky","Sunny","Clear","Dry"->{
                binding.conditionImg.setImageResource(R.drawable.sun);
            }

            "Partly Clouds","Clouds"->{
                binding.conditionImg.setImageResource(R.drawable.cloudysun2);
            }
            "Overcast","Mist","Foggy","Smoke"->{
                binding.conditionImg.setImageResource(R.drawable.fog);
            }


            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain","Rain"->{
                binding.conditionImg.setImageResource(R.drawable.raining);
            }

            "Freezing Rain","Light Snow","Heavy Snow","Moderate Snow","Blizzard" ->{
                binding.conditionImg.setImageResource(R.drawable.sleet);
            }
            "Haze" ->{
                binding.conditionImg.setImageResource(R.drawable.haze);
            }
            "Windy" ->{
                binding.conditionImg.setImageResource(R.drawable.windy);
            }
            "Thunderstorm","Severe Thunderstorm","Hailstorm" ->{
                binding.conditionImg.setImageResource(R.drawable.storm);
            }
            else->{
                binding.conditionImg.setImageResource(R.drawable.sun);
            }
        }
    }


}