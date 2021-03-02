package com.example.garam.search_trend.data

import com.google.gson.JsonObject

data class KeywordInfoData(

    val startDate : String,
    val endDate : String,
    val timeUnit : String,
    val keywordGroups : ArrayList<JsonObject>
//    val device : String?,
//    val gender : String?,
//    val ages : ArrayList<String>?

){
    data class KeywordGroup(
        val groupName : String,
        val keywords: ArrayList<String>
    )
}
