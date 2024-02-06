package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.widget.SearchView
import com.example.weatherapplication.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.max

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData("Jabalpur")
        SearchCity()
    }
//to change according to city Name
    private fun SearchCity() {
        val searchView= binding.searchView
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    fetchWeatherData(query)
                 }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityName:String) {
        //variable to get data from api and to tell that baseUrl is this..rest data can change but this will not
        val retrofit =Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        //now in response variable we'll get response
        val response = retrofit.getWeatherData(cityName, "6523f9a38390379b8341b4b7724b4a63","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody !=null) {
                    val temperature = responseBody.main.temp.toString()
                    val humidity = responseBody.main.humidity.toString()
                    val windSpeed =responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise
                    val sunSet = responseBody.sys.sunset
                    val seaLevel = responseBody.main.pressure
                    val condition =responseBody.weather.firstOrNull()?.main?:"unknown"
                    val maxTemp =responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min
//                    Log.d("TAG","$temperature")
                    binding.temp.text = "$temperature °C"
                    binding.weatherName.text= condition
                    binding.maxtemp.text="Max Temp: $maxTemp °C"
                    binding.mintemp.text="Min Temp: $minTemp °C"
                    binding.humidity.text="$humidity %"
                    binding.windSpeed.text="$windSpeed m/s"
                    binding.sunRise.text="${time(sunRise.toLong())}"
                    binding.sunSet.text="${time(sunSet.toLong())}"
                    binding.seaLevel.text="$seaLevel hPa"
                    binding.condition.text=condition
                    binding.dayName.text=dayName(System.currentTimeMillis())
                        binding.date.text=date()
                        binding.cityName.text="$cityName"

//                    changeImageAccordingToWeatherCondition(temperature,condition)
                    changeImgAccordingToTemp(temperature,condition)
                }

            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

        }

    private fun changeImgAccordingToTemp(temperature: String, condition: String) {
        if(temperature<="15" && (condition=="Light Snow"||condition=="Moderate Snow"||condition=="Heavy Snow"||condition=="Blizzard"||condition=="Snow"||condition=="Snowy"||condition=="Snow Showers"
            ||condition=="Snowstorm"||condition=="Freezing Temperatures"||condition=="Frost"||condition=="Icy Conditions"||condition=="Sleet"||condition=="Winter Storm"||condition=="Cold"
            ||condition=="Subzero Temperatures"||condition=="Snow Accumulation"||condition=="Snowfall"||condition=="Frozen"||condition=="Wintry Mix"||condition=="Hail"||condition=="Polar Vortex"||
            condition=="Snowdrifts"||condition=="Black Ice" ||condition=="Crisp Weather"||condition=="Clouds"||condition=="Cloudy"||condition=="Smoke"||condition=="Haze"||condition=="Clear")){
                binding.root.setBackgroundResource(R.drawable.snow_bg)
                binding.lottieAnimationView.setAnimation(R.raw.snow_json)
            }
        else if(temperature>="10" && (condition=="Light Rain"||condition=="Drizzle"||condition=="Moderate Rain"||condition=="Showers"||condition=="Heavy Rain"||condition=="Rain"||condition=="Rainy"||
                    condition=="Rain Showers"||condition=="Thunderstorms"||condition=="Stormy Weather"||condition=="Downpour"||condition=="Pouring Rain"||condition=="Precipitation"||condition=="Rainfall"||
                    condition=="Wet Weather"||condition=="Showers"||condition== "Scattered Showers"||condition=="Steady Rain"||condition=="Rainy and Windy"||condition=="Umbrella Weather"||condition=="Misty"||
                    condition=="Rainy Day")){
            binding.root.setBackgroundResource(R.drawable.rain_bg)
            binding.lottieAnimationView.setAnimation(R.raw.rain_json)
        }
        else if(temperature>="15" && (condition=="Partly Cloudy"||condition=="Overcast"||condition=="Mist"||condition=="Foggy"||condition=="Smoke"||condition=="Cloudy"||
                    condition=="Partly Clouds"||condition=="Mostly Cloudy"||condition=="Cloudy Skies"||condition=="gray Skies"||condition=="Heavy Cloudy"||condition=="Heavy  Clouds"||
                    condition=="Thick Cloud Cover"||condition=="Overcast Conditions"||condition=="Clouds"||condition=="Cloudy Throughout the Day"||
                    condition=="Scattered Clouds"||condition=="Dull"||condition=="Grey Weather"||condition=="Low Clouds"||condition=="Dark Clouds"||condition=="Gloomy"||condition=="Cloudy and Mild"||condition=="Hazy")){
            binding.root.setBackgroundResource(R.drawable.cloud_bg)
            binding.lottieAnimationView.setAnimation(R.raw.clouds_json)
        }
        else if(temperature>="18" && (condition=="Sunny"||condition=="Hot"||condition=="Clear Sky"||condition=="Clear"||condition=="High Temperature"||condition=="Clear Skies"||
            condition=="Sunshine"||condition== "Warm"||condition=="Heatwave"||condition=="Humid"||condition=="Beach Weather"||condition== "Clear and Bright"||condition=="Balmy"||
            condition=="Tropical"||condition=="Blue Skies"||condition=="UV Index"||condition=="Sweltering"||condition== "Summer Breeze"||condition=="Refreshing")){
                binding.root.setBackgroundResource(R.drawable.sun_bg)
                binding.lottieAnimationView.setAnimation(R.raw.sun_json)
        } else{
            binding.root.setBackgroundResource(R.drawable.sun_bg)
            binding.lottieAnimationView.setAnimation(R.raw.sun_json)
        }
        binding.lottieAnimationView.playAnimation()
    }

    private fun date(): String {
        val sdf=SimpleDateFormat("dd MM YYYY", Locale.getDefault())
        return sdf.format((Date()))
    }
    private fun time(timestamp:Long): String {
        val sdf=SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }

    fun dayName(timestamp:Long):String{
        val sdf=SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}


































//        private fun changeImageAccordingToWeatherCondition(temperature:String,condition:String) {
//        when(condition){
//            "Sunny","Hot","Clear Sky","Clear","High Temperature", "Clear Skies","Sunshine", "Warm","Scorching" ,"Heatwave",
//            "Humid","Beach Weather", "Clear and Bright", "Balmy" ,"Tropical" ,"Blue Skies","UV Index", "Sweltering", "Summer Breeze","Refreshing",
//            "Outdoor Activities"->{
//                binding.root.setBackgroundResource(R.drawable.sun_bg)
//                binding.lottieAnimationView.setAnimation(R.raw.sun_json)
//            }
//            "Partly Cloudy","Overcast","Mist","Foggy","Smoke","Cloudy","Partly Clouds","Mostly Cloudy","Cloudy Skies","Gray Skies","Heavy Cloudy","Heavy  Clouds","Thik Cloud Cover","Overcast Conditions",
//            "Thick Cloud Cover","Cloudy with Intermittent Sunshine","Cloudy Throughout the Day","Scattered Clouds","Dull","Grey Weather","Low Clouds","Dark Clouds","Gloomy","Cloudy and Mild","Hazy"->{
//                binding.root.setBackgroundResource(R.drawable.cloud_bg)
//                binding.lottieAnimationView.setAnimation(R.raw.clouds_json)
//            }
//            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain","Rain","Rainy","Rain Showers","Thunderstorms","Stormy Weather","Downpour","Pouring Rain","Precipitation","Rainfall","Wet Weather","Showers",
//            "Scattered Showers","Steady Rain","Rainy and Windy","Umbrella Weather","Misty","Rainy Day","Clouds"->{
//                binding.root.setBackgroundResource(R.drawable.rain_bg)
//                binding.lottieAnimationView.setAnimation(R.raw.rain_json)
//            }
//            "Light Snow","Moderate Snow","Headvy Snow","Blizzard","Snow","Snowy","Snow Showers","Snowstorm","Freezing Temperatures","Frost","Icy Conditions","Sleet","Winter Storm","Cold","Subzero Temperatures",
//            "Snow Accumulation","Snowfall","Frozen","Wintry Mix","Hail","Polar Vortex","Snowdrifts","Black Ice","Crisp Weather"->{
//                binding.root.setBackgroundResource(R.drawable.snow_bg)
//                binding.lottieAnimationView.setAnimation(R.raw.snow_json)
//            }
//            else->{
//                binding.root.setBackgroundResource(R.drawable.sun_bg)
//                binding.lottieAnimationView.setAnimation(R.raw.sun_json)
//            }
//        }

