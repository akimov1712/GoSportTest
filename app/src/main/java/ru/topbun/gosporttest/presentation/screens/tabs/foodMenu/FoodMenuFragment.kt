package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

    override fun setViews() {
        super.setViews()
        setBanner()
        val testItems = mutableListOf<FoodEntity>().apply{
            (0..100).forEach {
                add(
                    FoodEntity(
                        it,
                        "Пицца с сыром$it",
                        "The answers here are incorrect, although they're on the right track.\n" +
                                "You need to call Glide#clear(), not just set the image drawable to null. If you don't call clear(), an async load completing out of order may still cause view recycling issues. Your code should look like this:",
                        "fdsfds",
                        "https://p2.zoon.ru/7/e/540fbe0940c088c5138f3734_5e2e8b74e2d26.jpg"
                    )
                )
            }
        }
        binding.rvFoods.adapter = foodAdapter
        foodAdapter.submitList(testItems)
    }

    override fun observeViewModel() {
        super.observeViewModel()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect {
                    when (it) {
                        is FoodMenuState.Loading -> {

                        }

                        is FoodMenuState.ResultCategory -> {
                            it.category.forEach { chip ->
                                addChip(
                                    chip.name,
                                    binding.chipGroupCategory
                                )
                            }
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

    private fun setBanner() {
        binding.rvBanner.adapter = bannerAdapter
        bannerAdapter.itemList = listOf(
            ru.topbun.gosporttest.R.drawable.banner0,
            ru.topbun.gosporttest.R.drawable.banner1
        )
    }

}