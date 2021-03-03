package com.example.garam.search_trend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.garam.search_trend.data.KeywordInfoData
import com.example.garam.search_trend.data.ResponseData
import com.example.garam.search_trend.databinding.ActivityMainBinding
import com.example.garam.search_trend.network.NetworkController
import com.example.garam.search_trend.network.NetworkService
import com.example.garam.search_trend.viewModel.SearchViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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

    private lateinit var entry : ArrayList<Double>

//    private lateinit var viewModel : SearchViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val json = JSONObject()

        val jsonArray = JSONArray()

        jsonArray.put("한글")
        jsonArray.put("korean")

        json.put("groupName","한글")
        json.put("keywords",jsonArray)

        val testObj = JsonParser().parse(json.toString()) as JsonObject

        val test2 = KeywordInfoData("2017-04-01","2017-04-30","date", arrayListOf(testObj),null,null,null)

        Log.e("왜 안돼",test2.toString())


        var entries : ArrayList<Entry> = ArrayList()
        entries.add(Entry(0F,0F))
        val dataSet = LineDataSet(entries,"testLabel")

        var data : LineData = LineData(dataSet)


        binding.searchButton.setOnClickListener {

            networkService.trendSearch("ClientId","ClientSecret",test2).enqueue(object : Callback<ResponseData>{
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("Fda",t.message.toString())
                }

                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    Log.e("왜지",response.code().toString())
                    Log.e("zzz",response.body().toString())
                    entry = ArrayList()

                    for (i in 0 until response.body()!!.results[0].data.size) {
                        Log.e("ㅇㄻ",response.body()!!.results[0].data.size.toString())
                        entry.add(response.body()!!.results[0].data[i].ratio.toDouble())

                        data.addEntry(Entry(i.toFloat(),response.body()!!.results[0].data[i].ratio.toFloat()),0)
                        data.notifyDataChanged()
                        binding.lineChart.notifyDataSetChanged()
                        binding.lineChart.invalidate()

                        binding.lineChart.data = LineData(dataSet)

                        Log.e("무야호",response.body()!!.results[0].data[i].ratio)
                    }

                }
            })


        }
    }
}