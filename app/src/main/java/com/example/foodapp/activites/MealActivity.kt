package com.example.foodapp.activites

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.db.MealDatabase
import com.example.foodapp.fragments.HomeFragment
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.MealViewModel
import com.example.foodapp.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory= MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)



        getMealInformationFromIntent()
        setInformationInView()
        onYoutubeImageClick()
        onFavoriteClick()

        setContentView(R.layout.activity_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        var mealToSave:Meal?=null
        private fun ObserverMealDetailsLiveData(){
            mealMvvm.observerMealDetailsLiveData().observe(this,object : Observer<Meal>{
                override fun onChanged(value: Meal) {
                    val meal = value
                    mealToSave=meal


                    binding.tvInstructionsSteps.text=meal.strInstructions
                    youtubeLink= meal.strYoutube.toString()
                }
            })
        }
    }

    private fun onFavoriteClick() {

        binding.btnAddToFav.setOnClickListener(
            mealToSave?.let{
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun onYoutubeImageClick() {
        binding.imgYoutube.setOnClickListener({
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse((youtubeLink)))
        })
    }

    private fun setInformationInView() {
        Glide.with(applicationContext)
            .load(binding.imgMealDetail)

        binding.collapsingToolbar.title= mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }


}


