package ru.antares.food_project.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import eightbitlab.com.blurview.RenderScriptBlur
import ru.antares.food_project.Activity.DetailActivity
import ru.antares.food_project.databinding.ViewholderBestFoodBinding
import ru.antares.food_project.domain.Foods

class BestFoodAdapter(val items: MutableList<Foods>) : RecyclerView.Adapter<BestFoodAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestFoodAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewholderBestFoodBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BestFoodAdapter.ViewHolder, position: Int) {
        val binding = ViewholderBestFoodBinding.bind(holder.itemView)
        val item = items[position]

        binding.apply {
            titleTxt.text = item.title
            priceTxt.text = "${item.price}"
            timeTxt.text = "${item.timeId} мин"
            starTxt.text = item.star.toString()

            val radius = 10f
            val decorView: View = (holder.itemView.context as Activity).window.decorView
            val rootView: ViewGroup = decorView.findViewById(android.R.id.content) as ViewGroup
            val windowsBackground = decorView.background

            binding.blurView.setupWith(rootView,RenderScriptBlur(holder.itemView.context))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius)

            binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            binding.blurView.clipToOutline = true

            Glide.with(binding.root.context)
                .load(items[position].imagePath)
                .transform(CenterCrop(), RoundedCorners(30))
                .into(binding.img)

            holder.itemView.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("object",items[position])
                context.startActivity(intent)
            }
        }

    }


    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: ViewholderBestFoodBinding) : RecyclerView.ViewHolder(binding.root)
}