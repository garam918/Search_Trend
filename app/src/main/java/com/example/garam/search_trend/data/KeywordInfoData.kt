package com.example.garam.search_trend.data

import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName

data class KeywordInfoData(
    @SerializedName ("startDate") val startDate : String,
    @SerializedName ("endDate") val endDate : String,
    @SerializedName ("timeUnit") val timeUnit : String,
    @SerializedName ("keywordGroups") val keywordGroup : JsonArray,
    @SerializedName ("keywordGroups.groupName") val groupName : String,
    @SerializedName ("keywordGroups.keywords") val keywords : ArrayList<String>,
    @SerializedName ("device") val device : String?,
    @SerializedName ("gender") val gender : String?,
    @SerializedName ("ages") val ages : ArrayList<String>?
)
