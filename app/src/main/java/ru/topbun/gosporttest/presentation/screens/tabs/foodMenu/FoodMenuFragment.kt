package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.R
import ru.topbun.gosporttest.databinding.FragmentFoodMenuBinding
import ru.topbun.gosporttest.domain.entities.FoodEntity
import ru.topbun.gosporttest.presentation.base.BaseFragment
import ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.bannerAdapter.BannerAdapter
import ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.foods.FoodsAdapter

@AndroidEntryPoint
class FoodMenuFragment : BaseFragment<FragmentFoodMenuBinding>(FragmentFoodMenuBinding::inflate) {

    private val viewModel by viewModels<FoodMenuViewModel>()

    private val bannerAdapter by lazy { BannerAdapter() }
    private val foodAdapter by lazy { FoodsAdapter() }

    override fun observeViewModel() {
        super.observeViewModel()
        with(binding){
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.state.collect {
                        hideViewState()
                        when (it) {
                            is FoodMenuState.Loading -> {
                                pbLoader.visibility = View.VISIBLE
                            }

                            is FoodMenuState.ResultCategory -> {
                                it.category.forEach { chip ->
                                    addChip(
                                        chip.name,
                                        chipGroupCategory
                                    )
                                }
                            }

                            is FoodMenuState.ResultFoods -> {
                                rvFoods.visibility = View.VISIBLE
                                foodAdapter.submitList(it.foods)
                            }

                            is FoodMenuState.ErrorGetCategory -> {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                            }

                            else -> {}
                        }
                    }
                }
            }
        }
    }

    private fun hideViewState() = with(binding){
        rvFoods.isVisible = false
        pbLoader.isVisible = false
    }

    private fun addChip(title: String, chipGroup: ChipGroup) {
        val chip = Chip(requireContext())
        chip.text = title
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                chipGroup.removeView(chip)
                chipGroup.addView(chip, 0)
            }
        }
        chipGroup.addView(chip, chipGroup.childCount - 1)
    }

    override fun setAdapters() {
        super.setAdapters()
        setBanner()
        binding.rvFoods.adapter = foodAdapter
    }

    private fun setBanner() {
        binding.rvBanner.adapter = bannerAdapter
        bannerAdapter.itemList = listOf(
           R.drawable.banner0,
           R.drawable.banner1
        )
    }

}