package com.ibnu.saltnews.presentation.news.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ibnu.saltnews.data.model.Category
import com.ibnu.saltnews.R
import com.ibnu.saltnews.databinding.ItemCategoryBinding
import com.ibnu.saltnews.utils.popTap

class CategoryAdapter(private val itemHandler: CategoryItemHandler) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val listCategory = ArrayList<Category>()
    private var selectedPos = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listCategory: List<Category>) {
        this.listCategory.addAll(listCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = listCategory[position]
        holder.bind(category, position)
        holder.itemView.setOnClickListener {
            it.popTap()
            notifyItemChanged(selectedPos)
            selectedPos = holder.layoutPosition
            notifyItemChanged(selectedPos)
            Handler(Looper.getMainLooper()).postDelayed({
                itemHandler.onCategoryItemClicked(
                    category.name
                )
            }, 200)
        }
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category, position: Int) {

            if (selectedPos == position){
                binding.txvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_800))
            } else {
                binding.txvCategoryName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.grey_500))
            }

            binding.txvCategoryName.text = category.name

            Glide.with(binding.root.context)
                .load(category.imageUrl)
                .placeholder(R.color.input_color)
                .into(binding.imgCategory)
        }
    }

}