package com.ibnu.saltnews.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibnu.saltnews.data.model.Article
import com.ibnu.saltnews.data.remote.service.ApiResponse
import com.ibnu.saltnews.data.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getTopNews(category: String, query: String): LiveData<ApiResponse<List<Article>>> {
        val result = MutableLiveData<ApiResponse<List<Article>>>()
        viewModelScope.launch {
            newsRepository.getNewsArticles(category, query).collect {
                result.postValue(it)
            }
        }
        return result
    }

}