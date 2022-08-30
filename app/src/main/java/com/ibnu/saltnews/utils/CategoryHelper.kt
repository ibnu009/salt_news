package com.ibnu.saltnews.utils

import com.ibnu.saltnews.data.model.Category

class CategoryHelper {

    fun getCategories(): List<Category> {
        val categories = ArrayList<Category>()
        categories.add(Category(name = "General", value = "general" ,imageUrl = "https://static.dw.com/image/59636195_303.jpg"))
        categories.add(Category(name = "Business", value = "business" ,imageUrl = "https://businessexperttips.com/wp-content/uploads/2022/01/3.jpg"))
        categories.add(Category(name = "Fun", value = "entertainment" ,imageUrl = "https://jbacklund.com/wp-content/uploads/2019/11/EntertainmentHeader595x298.jpg"))
        categories.add(Category(name = "Health", value = "health" ,imageUrl = "https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg"))
        categories.add(Category(name = "Sports", value = "sports" ,imageUrl = "https://st.depositphotos.com/1875851/1555/i/600/depositphotos_15558827-stock-photo-balls-in-sport.jpg"))

        return categories
    }
}