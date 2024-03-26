package ru.topbun.gosporttest.presentation.screens

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.databinding.FragmentSplashBinding
import ru.topbun.gosporttest.presentation.base.BaseFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    override fun setViews() {
        super.setViews()
        startLoader()
    }

    private fun startLoader(){
        lifecycleScope.launch{
            var progress = 0
            while (progress < 100){
                binding.progressBar.progress = progress++
                delay(5)
            }
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToTabsFragment())
        }
    }

}