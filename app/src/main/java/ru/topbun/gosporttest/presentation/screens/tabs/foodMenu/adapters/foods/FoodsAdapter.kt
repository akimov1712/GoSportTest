package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.foods

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.topbun.gosporttest.databinding.ItemFoodBinding
import ru.topbun.gosporttest.domain.entities.FoodEntity
import kotlin.random.Random

class FoodsAdapter: ListAdapter<FoodEntity, FoodsAdapter.FoodsViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFoodBinding.inflate(inflater, parent, false)
        return FoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding){
            tvTitle.text = item.title
            tvDescr.text = item.descr
            val price = Random.nextInt(100, 501)
            btnBuy.text = "От $price р"
            Glide.with(holder.itemView.context).load(item.image).into(ivPreview)
        }
    }

    inner class FoodsViewHolder(val binding: ItemFoodBinding): RecyclerView.ViewHolder(binding.root)
}