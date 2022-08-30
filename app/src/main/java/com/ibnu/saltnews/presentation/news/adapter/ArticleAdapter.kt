package com.ibnu.saltnews.presentation.news.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibnu.saltnews.data.model.Article
import com.ibnu.saltnews.R
import com.ibnu.saltnews.databinding.ItemNewsBinding
import com.ibnu.saltnews.utils.parseToDateFormat
import com.ibnu.saltnews.utils.popTap

class ArticleAdapter(private val itemHandler: ArticleItemHandler) :
    RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    private var listArticle = ArrayList<Article>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listArticle: List<Article>) {
        this.listArticle.clear()
        this.listArticle.addAll(listArticle)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = listArticle[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = listArticle.size

    inner class ArticleViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {

            binding.root.setOnClickListener {
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    article.url?.let { it1 -> itemHandler.navigateToDetail(it1) }
                }, 200)
            }

            binding.txvNewsName.text = article.title
            binding.txvNewsDescription.text = article.description
            binding.txvPublishedDate.text = article.publishedAt?.parseToDateFormat()

            Glide.with(binding.root.context)
                .load(article.urlToImage)
                .placeholder(R.color.input_color)
                .into(binding.imgNews)
        }
    }
}