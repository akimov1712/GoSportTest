package ru.topbun.gosporttest.domain

import kotlin.math.E

sealed class RequestResult<E> {

    class Success<E>(val data: E): RequestResult<E>()
    class Error<E>(val message: String = "Возникла непредвиденная ошибка!"): RequestResult<E>()

}