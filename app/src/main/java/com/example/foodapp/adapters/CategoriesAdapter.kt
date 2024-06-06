package com.example.foodapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.CategoryItemsBinding
import com.example.foodapp.pojo.Category
import com.example.foodapp.pojo.CategoryList

class CategoriesAdapter :RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private var categoriesList =ArrayList<Category>()

    fun setCategoryList(CategoriesList:List<Category>){
        this.categoriesList= categoriesList
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding:CategoryItemBinding):ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesAdapter.CategoriesViewHolder {
        return CategoryViewHolder(
            CategoryItemsBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(CategoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryNmae.text=categoriesList[position].strCategory
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}