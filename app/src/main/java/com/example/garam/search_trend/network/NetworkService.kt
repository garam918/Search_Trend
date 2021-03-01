package com.example.garam.search_trend.network

import retrofit2.http.POST

interface NetworkService {

    @POST("/v1/datalab/search")
    fun trendSearch(

    )
}