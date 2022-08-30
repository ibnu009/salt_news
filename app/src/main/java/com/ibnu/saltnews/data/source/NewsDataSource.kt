package com.ibnu.saltnews.data.source

import com.ibnu.saltnews.data.model.Article
import com.ibnu.saltnews.data.remote.service.ApiResponse
import com.ibnu.saltnews.data.remote.service.NewsService
import com.ibnu.saltnews.utils.ConstValue.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsDataSource @Inject constructor(
    private val newsService: NewsService
) {
    suspend fun fetchNewsArticles(
        category: String,
        query: String,
    ): Flow<ApiResponse<List<Article>>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = newsService.getNewsArticles("us",category, query, API_KEY)
                if (response.totalResults > 0) {
                    emit(ApiResponse.Success(response.articles))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception){
                emit(ApiResponse.Error(e.message.toString()))
            }
        }
    }

}