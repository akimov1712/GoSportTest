package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.gosporttest.R
import ru.topbun.gosporttest.databinding.FragmentFoodMenuBinding
import ru.topbun.gosporttest.presentation.base.BaseFragment
import ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.bannerAdapter.BannerAdapter
import javax.inject.Inject


class FoodMenuFragment : BaseFragment<FragmentFoodMenuBinding>(FragmentFoodMenuBinding::inflate) {

    private val bannerAdapter by lazy{ BannerAdapter() }

    override fun setViews() {
        super.setViews()
        setBanner()
    }

    private fun setBanner() {
        binding.rvBanner.adapter = bannerAdapter
        bannerAdapter.itemList = listOf(R.drawable.banner0, R.drawable.banner1)
    }

}