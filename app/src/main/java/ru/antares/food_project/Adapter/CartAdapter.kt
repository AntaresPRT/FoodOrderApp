package ru.antares.food_project.Adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.light.foodastroapp.Helper.ManagmentCart
import eightbitlab.com.blurview.RenderScriptBlur
import ru.antares.food_project.Helper.ChangeNumberItemsListener
import ru.antares.food_project.databinding.ViewholderBestFoodBinding
import ru.antares.food_project.databinding.ViewholderCartBinding
import ru.antares.food_project.databinding.ViewholderCategoryBinding
import ru.antares.food_project.domain.Foods

class CartAdapter(
    private val listItemSelected: MutableList<Foods>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>(){

    private val managmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewholderCartBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val binding = ViewholderCartBinding.bind(holder.itemView)
        val item = listItemSelected[position]
        binding.apply {
            titleTxt.text = item.title
            feeEachItem.text = "${item.price}Р"
            totalEachTime.text = "${item.numberInCart} * ${item.numberInCart * item.price}Р"
            numTxt.text = item.numberInCart.toString()

            plusTxt.setOnClickListener {
                (listItemSelected as ArrayList<Foods>?)?.let { it ->
                    managmentCart.plusNumberItem(it, position, object: ChangeNumberItemsListener {
                        override fun change() {
                            changeNumberItemsListener.change()
                            notifyDataSetChanged()
                        }
                    })
                }
            }
            minusTxt.setOnClickListener {
                (listItemSelected as ArrayList<Foods>?)?.let { it ->
                    managmentCart.minusNumberItem(it, position, object: ChangeNumberItemsListener {
                        override fun change() {
                            changeNumberItemsListener.change()
                            notifyDataSetChanged()
                        }
                    })
                }
            }
            val radius = 10f
            val decorView: View = (holder.itemView.context as Activity).window.decorView
            val rootView: ViewGroup = decorView.findViewById(android.R.id.content) as ViewGroup
            val windowsBackground = decorView.background

            binding.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius)

            binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView.clipToOutline = true

            Glide.with(binding.root.context)
                .load(item.imagePath)
                .transform(CenterCrop(), RoundedCorners(30))
                .into(binding.img)
        }
    }

    override fun getItemCount() = listItemSelected.size

    inner class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)
}