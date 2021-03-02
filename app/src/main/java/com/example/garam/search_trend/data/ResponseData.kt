package com.example.garam.search_trend.data

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName ("startDate") val startDate : String,
    @SerializedName ("endDate") val endDate : String,
    @SerializedName ("timeUnit") val timeUnit : String,
    @SerializedName ("results") val results : ArrayList<Results>
) {
    data class Results(
        val title : String,
        val keywords: ArrayList<String>,
        @SerializedName ("data") val data : ArrayList<Data>)

    {
        data class Data(
            val period: String,
            val ratio: String
        )
    }
}
