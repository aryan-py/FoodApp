package com.example.foodapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.db.MealDatabase

class MealViewModelFactory {
    Val mealDatabase:MealDatabase
}:ViewModelProvider.Factory{
    override fun <T:ViewModel> create(modelClass: Class<T>):T{
        return MealViewModel(mealDatabase) as T
    }

}