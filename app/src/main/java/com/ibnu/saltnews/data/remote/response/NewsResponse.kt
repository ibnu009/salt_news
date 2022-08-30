package com.ibnu.saltnews.data.remote.response

import com.ibnu.saltnews.data.model.Article

data class NewsResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
