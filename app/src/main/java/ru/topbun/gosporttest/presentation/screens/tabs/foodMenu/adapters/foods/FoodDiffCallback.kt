package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.foods

import androidx.recyclerview.widget.DiffUtil
import ru.topbun.gosporttest.domain.entities.FoodEntity

class FoodDiffCallback: DiffUtil.ItemCallback<FoodEntity>() {

    override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
        return oldItem == newItem
    }
}