package ru.topbun.gosporttest.presentation.screens.tabs

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.gosporttest.R
import ru.topbun.gosporttest.databinding.FragmentTabsBinding
import ru.topbun.gosporttest.presentation.base.BaseFragment


@AndroidEntryPoint
class TabsFragment : BaseFragment<FragmentTabsBinding>(FragmentTabsBinding::inflate) {

    override fun setViews() {
        super.setViews()
        setBottomBar()
    }

    private fun setBottomBar() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.tabs_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.tabsBottomView.setupWithNavController(navController)
    }

}