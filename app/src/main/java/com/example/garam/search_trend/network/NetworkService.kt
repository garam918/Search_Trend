package com.example.garam.search_trend.network

import com.example.garam.search_trend.data.KeywordInfoData
import com.example.garam.search_trend.data.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface NetworkService {

    @POST("/v1/datalab/search")
    fun trendSearch(
        @Header("X-Naver-Client-Id") clientId : String,
        @Header("X-Naver-Client-Secret") clientSecret : String,
        @Body keywordInfo : KeywordInfoData
    ) : Call<ResponseData>
}