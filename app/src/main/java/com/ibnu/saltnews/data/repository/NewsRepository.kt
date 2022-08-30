package com.ibnu.saltnews.data.repository

import com.ibnu.saltnews.data.model.Article
import com.ibnu.saltnews.data.remote.service.ApiResponse
import com.ibnu.saltnews.data.source.NewsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val newsDataSource: NewsDataSource
) {

    suspend fun getNewsArticles(
        category: String,
        query: String,
    ): Flow<ApiResponse<List<Article>>> {
        return newsDataSource.fetchNewsArticles(category, query).flowOn(Dispatchers.IO)
    }

}