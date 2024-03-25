package ru.topbun.gosporttest.domain

import kotlin.math.E

sealed class RequestResult {

    class InProgress<E>(val data: E): RequestResult()
    class Success<E>(val data: E): RequestResult()
    class Error(val message: String = "Возникла непредвиденная ошибка!"): RequestResult()

}