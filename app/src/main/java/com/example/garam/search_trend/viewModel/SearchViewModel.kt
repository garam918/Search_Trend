package com.example.garam.search_trend.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.garam.search_trend.data.KeywordInfoData
import com.example.garam.search_trend.data.ResponseData
import com.example.garam.search_trend.network.NetworkController
import com.example.garam.search_trend.network.NetworkService
import com.github.mikephil.charting.charts.LineChart
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

class SearchViewModel(application: Application) : AndroidViewModel(application) {


    private val networkService : NetworkService by lazy {
        NetworkController.instance.networkService
    }

    val searchInfo = MutableLiveData<KeywordInfoData>()

    private lateinit var entry : ArrayList<Double>

    val groupName = MutableLiveData<String>()
    val keywords = MutableLiveData<String>()
 //   val dateRange = MutableLiveData<String>()

    val startDate = MutableLiveData<String>()
    val endDate = MutableLiveData<String>()

    fun setStartDate(date : String) {
        startDate.value = date
    }

    fun setEndDate(date: String) {
        endDate.value = date
    }

    fun searchTrend(lineChart: LineChart) {

        val json = JSONObject()
        val jsonArray = JSONArray()

        for(element in (keywords.value!!.split(","))) {
            jsonArray.put(element)
        }

        json.put("groupName",groupName.value.toString())
        json.put("keywords",jsonArray)

        val testObj = JsonParser().parse(json.toString()) as JsonObject

        val test2 = KeywordInfoData(startDate.value.toString(),endDate.value.toString()
            ,"date", arrayListOf(testObj),null,null,null)

        Log.e("왜 안돼2",test2.toString())


        var entries : ArrayList<Entry> = ArrayList()
        entries.add(Entry(0F,0F))
        val dataSet = LineDataSet(entries,groupName.value.toString())

        var data : LineData = LineData(dataSet)

        networkService.trendSearch("ClientId","ClientSecret",test2).enqueue(object :
            Callback<ResponseData> {
            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("Fda",t.message.toString())
            }

            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                Log.e("왜지",response.code().toString())
                Log.e("zzz",response.body().toString())
                entry = ArrayList()

                for (i in 0 until response.body()!!.results[0].data.size) {

                    entry.add(response.body()!!.results[0].data[i].ratio.toDouble())

                    data.addEntry(Entry(i.toFloat(),response.body()!!.results[0].data[i].ratio.toFloat()),0)
                    data.notifyDataChanged()
                    lineChart.notifyDataSetChanged()
                    lineChart.invalidate()

                    lineChart.data = LineData(dataSet)

                    Log.e("무야호",response.body()!!.results[0].data[i].ratio)
                }

            }
        })
    }


}