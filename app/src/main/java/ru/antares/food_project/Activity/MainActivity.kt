package ru.antares.food_project.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import ru.antares.food_project.R
import ru.antares.food_project.Adapter.BestFoodAdapter
import ru.antares.food_project.Adapter.CategoryAdapter
import ru.antares.food_project.domain.Location
import ru.antares.food_project.domain.Price
import ru.antares.food_project.domain.Time
import ru.antares.food_project.databinding.ActivityMainBinding
import ru.antares.food_project.domain.Category
import ru.antares.food_project.domain.Foods

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val bestFoodAdapter by lazy { BestFoodAdapter(mutableListOf()) }
    private val categoryAdapter by lazy { CategoryAdapter(mutableListOf()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLocation()
        initPrice()
        initTime()
        initBestFood()
        initCategory()
        setVariables()
    }

    private fun setVariables() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity,CartActivity::class.java))
        }
        binding.searchBtn.setOnClickListener {
            val searchText = binding.searchInput.text.toString().trim()
            if(searchText.isNotEmpty()) {
                val intent = Intent(this, ListFoodActivity::class.java)
                intent.putExtra("text", searchText)
                intent.putExtra("isSearch",true)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Поиск...", Toast.LENGTH_SHORT).show()
            }
         }
    }

    private fun initCategory() {
        val myRef : DatabaseReference = database.getReference("Category")
        binding.progressBarCategory.visibility = View.VISIBLE
        val list: MutableList<Category> = mutableListOf()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(issue in snapshot.children) {
                    val category = issue.getValue(Category::class.java)
                    if(category != null) {
                        list.add(category)
                    }
                }
                if(list.isNotEmpty()) {
                    binding.categoryView.layoutManager = GridLayoutManager(this@MainActivity,4)
                    categoryAdapter.items.addAll(list)
                    binding.categoryView.adapter = categoryAdapter
                }
                binding.progressBarCategory.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
            }

        })
    }

    private fun initBestFood() {
        val myRef : DatabaseReference = database.getReference("Foods")
        binding.progressBarBestFood.visibility = View.VISIBLE
        val list: MutableList<Foods> = mutableListOf()
        val query: Query = myRef.orderByChild("BestFood").equalTo(true)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(issue in snapshot.children) {
                    val foods = issue.getValue(Foods::class.java)
                    if(foods != null) {
                        list.add(foods)
                    }
                }
                if(list.isNotEmpty()) {
                    binding.bestFoodView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
                    bestFoodAdapter.items.addAll(list)
                    binding.bestFoodView.adapter = bestFoodAdapter
                }
                binding.progressBarBestFood.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBarBestFood.visibility = View.GONE
                Log.e("MainActivity", "Database error: ${error.message}")
            }

        })
    }

    private fun initLocation() {
        val myRef : DatabaseReference = database.getReference("Location")
        val list: MutableList<Location> = mutableListOf()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                    for(issue in snapshot.children) {
                        val location = issue.getValue(Location::class.java)
                        if(location != null) {
                            list.add(location)
                        }
                    }
                    val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.locationSp.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
            }

        })
    }
    private fun initPrice() {
        val myRef : DatabaseReference = database.getReference("Price")
        val list: MutableList<Price> = mutableListOf()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(issue in snapshot.children) {
                    val price = issue.getValue(Price::class.java)
                    if(price != null) {
                        list.add(price)
                    }
                }
                val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.priceSp.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
            }

        })
    }
    private fun initTime() {
        val myRef : DatabaseReference = database.getReference("Time")
        val list: MutableList<Time> = mutableListOf()

        myRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(issue in snapshot.children) {
                    val time = issue.getValue(Time::class.java)
                    if(time != null) {
                        list.add(time)
                    }
                }
                val adapter = ArrayAdapter(this@MainActivity, R.layout.sp_item, list)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.timeSp.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", "Database error: ${error.message}")
            }

        })
    }
}