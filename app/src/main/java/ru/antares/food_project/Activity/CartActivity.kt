package ru.antares.food_project.Activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.light.foodastroapp.Helper.ManagmentCart
import eightbitlab.com.blurview.RenderScriptBlur
import ru.antares.food_project.Adapter.CartAdapter
import ru.antares.food_project.Helper.ChangeNumberItemsListener
import ru.antares.food_project.R
import ru.antares.food_project.databinding.ActivityCartBinding
import ru.antares.food_project.databinding.ActivityDetailBinding
import kotlin.math.roundToInt

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter
    private lateinit var managmentCart: ManagmentCart
    var tax = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managmentCart = ManagmentCart(this)

        cartAdapter = CartAdapter(
            managmentCart.listCart(),
            this@CartActivity,
            object : ChangeNumberItemsListener {
                override fun change() {
                    calculateCart()
                }
            }
        )
        setVariables()
        calculateCart()
        initList()
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

    private fun initList() {
        if(managmentCart.listCart().isNotEmpty()) {
            binding.emptyTxt.visibility =  View.VISIBLE
            binding.scrollView.visibility =  View.VISIBLE
        } else {
            binding.emptyTxt.visibility =  View.GONE
            binding.scrollView.visibility =  View.VISIBLE
        }

        binding.cartView.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
        binding.cartView.adapter = cartAdapter
    }

    private fun setVariables() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10
        tax = ((managmentCart.totalFee() * percentTax * 100).roundToInt() / 100).toDouble()

        val total = ((managmentCart.totalFee() + tax + delivery) * 100).roundToInt() / 100
        val itemTotal = (managmentCart.totalFee() * 100).roundToInt() / 100

        binding.apply {
            totalFreeTxt.text = "${itemTotal}Р"
            taxTxt.text = "${tax}Р"
            deliveryTxt.text = "${delivery}Р"
            totalTxt.text = "${total}Р"
        }

        cartAdapter.notifyDataSetChanged()
    }
}