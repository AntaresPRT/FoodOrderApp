package ru.antares.food_project.Activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.light.foodastroapp.Helper.ManagmentCart
import eightbitlab.com.blurview.RenderScriptBlur
import ru.antares.food_project.R
import ru.antares.food_project.databinding.ActivityDetailBinding
import ru.antares.food_project.domain.Foods

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var foodItems: Foods
    private var num: Int = 1
    private lateinit var managementCart: ManagmentCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagmentCart(this)

        getBundleExtras()
        setVariables()
        setBlurEffect()
    }

    private fun setBlurEffect() {
        val radius = 10f
        val decorView: View = (this).window.decorView
        val rootView: ViewGroup = decorView.findViewById(android.R.id.content) as ViewGroup
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        binding.blurView2.setupWith(rootView, RenderScriptBlur(this))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView2.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView2.clipToOutline = true
    }

    private fun setVariables() {
        binding.backBtn.setOnClickListener { finish() }

        Glide.with(this)
            .load(foodItems.imagePath)
            .into(binding.pic)

        binding.apply {
            priceTxt.text = "" + foodItems.price + "Р"
            titleTxt.text = foodItems.title
            descriptionTxt.text = foodItems.description
            ratingTxt.text = foodItems.star.toString()
            ratingBar.rating = foodItems.star.toFloat()
            totalTxt.text = (num * foodItems.price).toString() + "Р"

            plusTxt.setOnClickListener{
                    num += 1
                    numTxt.text = num.toString()
                    totalTxt.text = (num * foodItems.price).toString() + "Р"
            }

            minusTxt.setOnClickListener{
                if(num > 1) {
                    num -= 1
                    numTxt.text = num.toString()
                    totalTxt.text = (num * foodItems.price).toString() + "Р"
                }
            }

            addBtn.setOnClickListener {
                foodItems.numberInCart = num
                managementCart.insertFood(foodItems)
            }
        }
    }

    private fun getBundleExtras() {
        foodItems = intent.getSerializableExtra("object") as Foods
    }
}