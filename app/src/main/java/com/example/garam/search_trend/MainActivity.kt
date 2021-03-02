package com.example.garam.search_trend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.garam.search_trend.data.KeywordInfoData
import com.example.garam.search_trend.data.ResponseData
import com.example.garam.search_trend.network.NetworkController
import com.example.garam.search_trend.network.NetworkService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val networkService : NetworkService by lazy {
        NetworkController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val json = JSONObject()

        val jsonArray = JSONArray()

        jsonArray.put("한글")
        jsonArray.put("korean")

        json.put("groupName","한글")
        json.put("keywords",jsonArray)

        val testObj = JsonParser().parse(json.toString()) as JsonObject

        val test2 = KeywordInfoData("2017-01-01","2017-04-30","month", arrayListOf(testObj))

        Log.e("왜 안돼",test2.toString())

        networkService.trendSearch("ClientId","ClientSecret",test2).enqueue(object : Callback<ResponseData>{
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("Fda",t.message.toString())
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                Log.e("왜지",response.code().toString())
                Log.e("zzz",response.body().toString())

            }
        })

    }
}