package com.example.foodapp.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitIInstance
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class MealViewModel {
    private val viewModelScope: Any
        get() {
            TODO()
        }
    val mealDatabase:MealDatabase = TODO()

    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitIInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{

           override fun onResponse(call : Call<MealList>, response: Response<MealList>){
                 if(response.body()!=null){
                     mealDetailsLiveData.value= response.body()!!.meals[0]
                 }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch{
            mealDatabase.mealDao().upsertMeal(meal)
        }
    }

    fun deleteMeal(meal:Meal){
        viewModelScope.launch{
            mealDatabase.mealDao().delete(meal)
        }
    }
}