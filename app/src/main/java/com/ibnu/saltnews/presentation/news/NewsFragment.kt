package com.ibnu.saltnews.presentation.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibnu.saltnews.data.remote.service.ApiResponse
import com.ibnu.saltnews.presentation.news.adapter.ArticleAdapter
import com.ibnu.saltnews.presentation.news.adapter.ArticleItemHandler
import com.ibnu.saltnews.presentation.news.adapter.CategoryAdapter
import com.ibnu.saltnews.presentation.news.adapter.CategoryItemHandler
import com.ibnu.saltnews.databinding.FragmentNewsBinding
import com.ibnu.saltnews.utils.CategoryHelper
import com.ibnu.saltnews.utils.showExitAppDialog
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class NewsFragment : Fragment(), ArticleItemHandler, CategoryItemHandler {

    private val viewModel: NewsViewModel by viewModels()

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var categoriesAdapter: CategoryAdapter

    private var isAlreadyLoadingShimmering = false
    private var isSearching = false
    private var selectedCategory = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().showExitAppDialog()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateAdapters()
        categoriesAdapter.setData(CategoryHelper().getCategories())
        showNews(selectedCategory, "")

        binding.svNews.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showNews(selectedCategory, query ?: "")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.isBlank() == true && isSearching) {
                    showNews(selectedCategory,  "")
                    isSearching = false
                }
                return false
            }
        })
    }

    private fun initiateAdapters() {
        articleAdapter = ArticleAdapter(this)
        binding.rvMenu.apply {
            layoutManager =
                LinearLayoutManager(requireContext(),  LinearLayoutManager.VERTICAL, false)
            adapter = articleAdapter
        }

        categoriesAdapter = CategoryAdapter(this)
        binding.rvCategories.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }


    private fun showNews(category: String, query: String) {
        viewModel.getTopNews(category, query).observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResponse.Loading -> {
                    Timber.d("Loading")
                    showLoading(true)
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    Timber.d("Error ${response.errorMessage}")
                }
                is ApiResponse.Success -> {
                    showLoading(false)
                    binding.txvEmptyText.visibility = View.GONE
                    binding.rvMenu.visibility = View.VISIBLE
                    articleAdapter.setData(response.data)
                }
                is ApiResponse.Empty -> {
                    binding.txvEmptyText.visibility = View.VISIBLE
                    binding.rvMenu.visibility = View.INVISIBLE
                    showLoading(false)
                }
                else -> {
                    showLoading(false)
                    Timber.d("Unknown Error")
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (!isAlreadyLoadingShimmering) {
            if (isLoading) {
                binding.shimmeringLoadingHome.startShimmer()
                binding.shimmeringLoadingHome.showShimmer(true)
                binding.loadingLayout.visibility = View.VISIBLE
            } else {
                binding.shimmeringLoadingHome.stopShimmer()
                binding.shimmeringLoadingHome.showShimmer(false)
                binding.loadingLayout.visibility = View.GONE
                isAlreadyLoadingShimmering = true
            }
        } else {
            if (isLoading) {
                binding.loadingCircular.visibility = View.VISIBLE
            } else {
                binding.loadingCircular.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun navigateToDetail(articleUrl: String) {
        val action = NewsFragmentDirections.actionNewsFragmentToNewsWebViewFragment(articleUrl)
        findNavController().navigate(action)
    }

    override fun onCategoryItemClicked(name: String) {
        selectedCategory = name
        showNews(name, "")
    }

}