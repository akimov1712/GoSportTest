package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.bannerAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.topbun.gosporttest.databinding.ItemBannerBinding
import javax.inject.Inject

class BannerAdapter: RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    var itemList = listOf<Int>()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerBinding.inflate(inflater, parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val item = itemList[position]
        holder.binding.root.setImageResource(item)
    }

    override fun getItemCount() = itemList.count()

    inner class BannerViewHolder(val binding: ItemBannerBinding): RecyclerView.ViewHolder(binding.root)

}