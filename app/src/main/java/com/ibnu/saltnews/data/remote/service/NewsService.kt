package com.ibnu.saltnews.data.remote.service

import com.ibnu.saltnews.data.remote.response.NewsResponse
import retrofit2.http.*

interface NewsService {

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String,
        @Query("apikey") apiKey: String
    ): NewsResponse

}