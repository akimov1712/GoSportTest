package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.entities.CategoryEntity
import ru.topbun.gosporttest.domain.useCases.GetCategoryListUseCase
import javax.inject.Inject

@HiltViewModel
class FoodMenuViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase
): ViewModel() {

    private val _state = MutableStateFlow<FoodMenuState>(FoodMenuState.Initial)
    val state get() = _state.asStateFlow()

    private fun getCategoryList() = viewModelScope.launch {
        getCategoryListUseCase().collect{
            when(it){
                is RequestResult.Error<*> -> {
                    _state.emit(FoodMenuState.ErrorGetCategory("Не удалось загрузить категории"))
                }
                is RequestResult.Success -> {
                    _state.emit(FoodMenuState.ResultCategory(it.data))
                }
            }
        }
    }

    init {
        getCategoryList()
    }
}