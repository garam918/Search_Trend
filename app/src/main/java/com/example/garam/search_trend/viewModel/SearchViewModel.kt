package com.example.garam.search_trend.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.garam.search_trend.BuildConfig
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

    private val context = getApplication<Application>().applicationContext

    val dateRangeValue = MutableLiveData<String>()
    val deviceValue = MutableLiveData<String>()
    val genderValue = MutableLiveData<String>()

    init {
        dateRangeValue.value = "date"
        deviceValue.value = null
        genderValue.value = null
    }

    private lateinit var entry : ArrayList<Any>

    val groupName = MutableLiveData<String>()
    val keywords = MutableLiveData<String>()

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

        if (nullCheck(groupName.value, keywords.value, startDate.value, endDate.value)) {
            for(element in (keywords.value!!.split(","))) {
                jsonArray.put(element)
            }
            json.put("groupName",groupName.value.toString())
            json.put("keywords",jsonArray)

            val jsonObject = JsonParser().parse(json.toString()) as JsonObject

            val keywordInfoObject = KeywordInfoData(startDate.value.toString(),endDate.value.toString(),dateRangeValue.value.toString(), arrayListOf(jsonObject),deviceValue.value,genderValue.value,null)

            val entries : ArrayList<Entry> = ArrayList()
            entries.add(Entry(0F,0F))
            val dataSet = LineDataSet(entries,groupName.value.toString())

            val data = LineData(dataSet)

            networkService.trendSearch(BuildConfig.naver_client_id,BuildConfig.naver_client_secret,keywordInfoObject).enqueue(object :
                Callback<ResponseData> {
                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Log.e("Fda",t.message.toString())
                }

                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    Log.e("왜지",response.code().toString())
                    Log.e("zzz",response.body().toString())
                    entry = ArrayList()

                    val responseData = response.body()!!.results[0].data

                    for (i in 0 until responseData.size) {

                        entry.add(responseData[i].ratio.toDouble())

                        data.addEntry(Entry(i.toFloat(),responseData[i].ratio.toFloat()),0)
                        data.notifyDataChanged()
                        lineChart.notifyDataSetChanged()
                        lineChart.invalidate()

                        lineChart.data = LineData(dataSet)

                    }

                }
            })
        }
    }

    private fun nullCheck(groupName: String?, keywords:String?, startDate: String?, endDate: String?) : Boolean{
        when {
            groupName == null -> {
                Toast.makeText(context,"주제어를 입력해주세요",Toast.LENGTH_SHORT).show()
                return false
            }
            keywords == null -> {
                Toast.makeText(context,"검색어를 입력해주세요",Toast.LENGTH_SHORT).show()
                return false
            }

            startDate == null -> {
                Toast.makeText(context,"시작 날짜를 입력해주세요",Toast.LENGTH_SHORT).show()
                return false
            }

            endDate == null -> {
                Toast.makeText(context,"종료 날짜를 입력해주세요",Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

}