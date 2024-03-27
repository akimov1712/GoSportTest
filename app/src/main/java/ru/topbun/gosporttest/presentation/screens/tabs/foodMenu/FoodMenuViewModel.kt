package ru.topbun.gosporttest.presentation.screens.tabs.foodMenu

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.topbun.gosporttest.domain.RequestResult
import ru.topbun.gosporttest.domain.useCases.GetCategoryListUseCase
import ru.topbun.gosporttest.domain.useCases.GetFoodCategoryUseCase
import ru.topbun.gosporttest.domain.useCases.GetFoodListUseCase
import javax.inject.Inject

@HiltViewModel
class FoodMenuViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCategoryListUseCase,
    private val getFoodCategoryUseCase: GetFoodCategoryUseCase,
    private val getFoodListUseCase: GetFoodListUseCase
): ViewModel() {

    private val _state = MutableStateFlow<FoodMenuState>(FoodMenuState.Initial)
    val state get() = _state.asStateFlow()

    fun getFoodList() = viewModelScope.launch {
        _state.emit(FoodMenuState.Loading)
        Log.d("FFFT", "перед коллект")
        getFoodListUseCase().collect{
            Log.d("FFFT", "после коллект")
            when(it){
                is RequestResult.Error<*> -> {
                    Log.d("FFFT", "ошибка")
                    _state.emit(FoodMenuState.ErrorGetFoods(it.message))
                }
                is RequestResult.Success -> {
                    Log.d("FFFT", "пришло")
                    _state.emit(FoodMenuState.ResultFoods(it.data))
                }
            }
        }
    }

    fun getFoodListCategory(category: String) = viewModelScope.launch {
        _state.emit(FoodMenuState.Loading)
        getFoodCategoryUseCase(category).collect{
            when(it){
                is RequestResult.Error<*> -> {
                    _state.emit(FoodMenuState.ErrorGetFoods(it.message))
                }
                is RequestResult.Success -> {
                    _state.emit(FoodMenuState.ResultFoods(it.data))
                }
            }
        }
    }

    fun getCategoryList() = viewModelScope.launch {
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