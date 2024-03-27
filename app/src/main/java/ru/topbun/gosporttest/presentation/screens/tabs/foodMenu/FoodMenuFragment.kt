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
import ru.topbun.gosporttest.presentation.base.BaseFragment
import ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.bannerAdapter.BannerAdapter
import ru.topbun.gosporttest.presentation.screens.tabs.foodMenu.adapters.foods.FoodsAdapter

@AndroidEntryPoint
class FoodMenuFragment : BaseFragment<FragmentFoodMenuBinding>(FragmentFoodMenuBinding::inflate) {

    private val viewModel by viewModels<FoodMenuViewModel>()

    private val bannerAdapter by lazy { BannerAdapter() }
    private val foodAdapter by lazy { FoodsAdapter() }

    private var choiceCategory: String? = null

    override fun observeViewModel() {
        super.observeViewModel()
        with(binding){
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.state.collect {
                        when (it) {
                            is FoodMenuState.Loading -> {
                                hideViewState()
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

                            is FoodMenuState.ErrorGetFoods -> {
                                hideViewState()
                                tvError.text = it.message
                                layoutError.visibility = View.VISIBLE

                            }

                            is FoodMenuState.ResultFoods -> {
                                hideViewState()
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

    override fun setListenersInView() {
        super.setListenersInView()
        with(binding){
            btnError.setOnClickListener {
                choiceCategory?.let { viewModel.getFoodListCategory(it) } ?: viewModel.getFoodList()
            }
            chipGroupCategory.setOnCheckedStateChangeListener { group, checkedIds ->
                val checkedId = chipGroupCategory.checkedChipId
                if (checkedId == View.NO_ID) {
                    choiceCategory = null
                    viewModel.getFoodList()
                }
            }

        }
    }

    private fun hideViewState() = with(binding){
        rvFoods.isVisible = false
        pbLoader.isVisible = false
        layoutError.isVisible = false
    }

    private fun addChip(title: String, chipGroup: ChipGroup) {
        val chip = Chip(requireContext())
        chip.text = title
        chip.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                chipGroup.removeView(chip)
                chipGroup.addView(chip, 0)
                val nameCategory = buttonView.text.toString()
                choiceCategory = nameCategory
                viewModel.getFoodListCategory(nameCategory)
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