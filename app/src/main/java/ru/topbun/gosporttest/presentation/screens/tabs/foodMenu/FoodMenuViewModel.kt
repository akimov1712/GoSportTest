package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.useCases.GetCategoryListUseCase
import ru.topbun.gosporttest.domain.useCases.GetFoodListUseCase
import javax.inject.Inject

@HiltViewModel
class FoodMenuViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getFoodListUseCase: GetFoodListUseCase
): ViewModel() {

    private val _state = MutableStateFlow<FoodMenuState>(FoodMenuState.Initial)
    val state get() = _state.asStateFlow()

    private fun getFoodList() = viewModelScope.launch {
        getFoodListUseCase().collect{
            Log.d("FFFT", "вью модель")
            when(it){
                is RequestResult.Error<*> -> {
                    Log.d("FFFT", "вью модель ошибка")
                    _state.emit(FoodMenuState.ErrorGetFoods(it.message))
                }
                is RequestResult.Success -> {
                    Log.d("FFFT", "вью модель удачно")
                    _state.emit(FoodMenuState.ResultFoods(it.data))
                }
            }
        }
    }

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
        getFoodList()
        getCategoryList()
    }
}