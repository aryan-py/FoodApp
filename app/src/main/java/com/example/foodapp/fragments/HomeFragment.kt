package com.example.foodapp.fragments



import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.foodapp.fragments.databinding.FragmentHomeBinding
import com.example.foodapp.pojo.Meal
import com.example.foodapp.viewModel.HomeViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.activites.MealActivity
import com.example.foodapp.adapters.CategoriesAdapter
import com.example.foodapp.adapters.MostPopularAdapter
import com.example.foodapp.pojo.MealsByCategory


class HomeFragment<FragmentHomeBinding> : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemdAdapter: MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID ="package com.example.foodapp.fragments.idMeal"
        const val MEAL_NAME ="package com.example.foodapp.fragments.nameMeal"
        const val MEAL_THUMB ="package com.example.foodapp.fragments.thumbMeal"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm= ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemdAdapter= MostPopularAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        homeMvvm.getRandomMeal()
        observerRandomMeal()
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        homeMvvm.getCategories()
        observeCategoriesLiveData()

        prepeareCategoriesRecyclerView()

    }

    private fun prepeareCategoriesRecyclerView() {
        binding.recViewCategories.apply{
            layoutManager =GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {

        homeMvvm.observerCategoriesLiveData().observe((viewLifecycleOwner,Observer{categories->

                categoriesAdapter.setCategoryList(categories)

        })
    }

    private fun onPopularItemClick() {
        popularItemdAdapter.onItemClick={ meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)

        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply{
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter= popularItemdAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observerPopularItemsLiveData().observe(viewLifecycleOwner
        ,{ mealList->
            popularItemdAdapter.setMeals(mealList=mealList as ArrayList<MealsByCategory>)

        })
    }

    private fun onRandomMealClick(){
        binding.randomMealCard.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME, randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
            
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomeMeaLlivedata().observe(viewLifecycleOwner,{
            meal ->
               Glide.with(this@HomeFragment)
                   .load(meal!!.strMealThumb)
                   .into(binding.imgRandomMeal)

                   this.randomMeal=meal



        })
    }

}