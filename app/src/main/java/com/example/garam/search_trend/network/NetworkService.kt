package com.example.garam.search_trend.network

import com.example.garam.search_trend.data.KeywordInfoData
import com.example.garam.search_trend.data.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkService {

    @POST("/v1/datalab/search")
    fun trendSearch(
        @Body keywordInfo : KeywordInfoData
    ) : Call<ResponseData>
}