package ru.antares.food_project.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import eightbitlab.com.blurview.RenderScriptBlur
import ru.antares.food_project.Activity.ListFoodActivity
import ru.antares.food_project.databinding.ViewholderCategoryBinding
import ru.antares.food_project.domain.Category


class CategoryAdapter(val items: MutableList<Category>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewholderCategoryBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        val binding = ViewholderCategoryBinding.bind(holder.itemView)
        val item = items[position]

        binding.titleCatTxt.text = item.name

        val radius = 10f
        val decorView: View = (holder.itemView.context as Activity).window.decorView
        val rootView: ViewGroup = decorView.findViewById(android.R.id.content) as ViewGroup
        val windowsBackground = decorView.background

        binding.blurView.setupWith(rootView, RenderScriptBlur(holder.itemView.context))
            .setFrameClearDrawable(windowsBackground)
            .setBlurRadius(radius)

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        val drawableResourceId: Int = binding.root.resources.getIdentifier(
            item.imagePath,
            "drawable",
            binding.root.context.packageName
        )

        Glide.with(binding.root.context)
            .load(drawableResourceId)
            .into(binding.imgCat)

        holder.itemView.setOnClickListener {
            val context = binding.root.context
            val intent = Intent(context, ListFoodActivity::class.java)
            intent.putExtra("categoryObject",items[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root)
}