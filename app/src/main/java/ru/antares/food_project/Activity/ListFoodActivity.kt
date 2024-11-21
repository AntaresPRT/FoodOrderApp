package ru.antares.food_project.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import ru.antares.food_project.Adapter.ListFoodAdapter
import ru.antares.food_project.R
import ru.antares.food_project.databinding.ActivityListFoodBinding
import ru.antares.food_project.domain.Category
import ru.antares.food_project.domain.Foods

class ListFoodActivity : BaseActivity() {

    private lateinit var binding: ActivityListFoodBinding
    private val listFoodAdapter by lazy { ListFoodAdapter(mutableListOf()) }
    private var categoryId: Int = 0
    private var categoryName: String = ""
    private var searchText: String = ""
    private var isSearch: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setIntentExtra()
        initList()
    }

    private fun initList() {
        val myRef : DatabaseReference = database.getReference("Foods")
        binding.progressBar.visibility = View.VISIBLE
        val list: MutableList<Foods> = mutableListOf()
        val query: Query

        if(isSearch) {
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff')
        } else {
            query = myRef.orderByChild("CategoryId").equalTo(categoryId.toDouble())
        }

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(issue in snapshot.children) {
                    val foods = issue.getValue(Foods::class.java)
                    if(foods != null) {
                        list.add(foods)
                    }
                }

                    binding.foodListView.layoutManager = GridLayoutManager(this@ListFoodActivity,2)
                    listFoodAdapter.items.addAll(list)
                    binding.foodListView.adapter = listFoodAdapter

                binding.progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.progressBar.visibility = View.GONE
                Log.e("ListFoodActivity", "Database error: ${error.message}")
            }

        })
    }

    private fun setIntentExtra() {
        searchText = intent.getStringExtra("text") ?: ""
        isSearch = intent.getBooleanExtra("isSearch", false)

        if (!isSearch) {
            val category = intent.getSerializableExtra("categoryObject") as? Category
            category?.let {
                categoryId = it.id
                categoryName = it.name
                binding.titleTxt.text = categoryName
            }
        }

        binding.backBtn.setOnClickListener { finish() }
    }
}