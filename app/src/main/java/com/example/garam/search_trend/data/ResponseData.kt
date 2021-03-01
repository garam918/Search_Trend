package com.example.garam.search_trend.data

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName ("startDate") val startDate : String,
    @SerializedName ("endDate") val endDate : String,
    @SerializedName ("timeUnit") val timeUnit : String,
    @SerializedName ("results.title") val title : String,
    @SerializedName ("results.keywords") val keywords : ArrayList<String>,
    @SerializedName ("results.data.period") val period : String,
    @SerializedName ("results.data.ratio") val ratio : String
)
