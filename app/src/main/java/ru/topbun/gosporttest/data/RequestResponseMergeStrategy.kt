package ru.topbun.gosporttest.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.lastOrNull
import ru.topbun.gosporttest.domain.RequestResult
import javax.inject.Inject

interface MergeStrategy<E> {
    fun <E> merge(cache: RequestResult<E>, server: RequestResult<E>): RequestResult<E>
}


class RequestResponseMergeStrategy<E> @Inject constructor(): MergeStrategy<E> {
    override fun <E> merge(
        cache: RequestResult<E>,
        server: RequestResult<E>
    ): RequestResult<E> {
        return when{
            cache is RequestResult.Success<E> && server is RequestResult.Success<E> ->
                merge(cache, server)
            cache is RequestResult.Success<E> && server is RequestResult.Error<*> ->
                merge(cache, server)
            cache is RequestResult.Error<*> && server is RequestResult.Success<E> ->
                merge(cache, server)
            cache is RequestResult.Error<*> && server is RequestResult.Error<*> ->
                merge(cache, server as RequestResult.Error<*>)
            else -> {
                error("Unimplemented branch")
            }
        }
    }

    private fun <E>merge(
        cache: RequestResult.Success<E>,
        server: RequestResult.Success<E>
    ): RequestResult<E> {
        return RequestResult.Success(server.data)
    }

    private fun <E>merge(
        cache: RequestResult.Success<E>,
        server: RequestResult.Error<*>
    ): RequestResult<E> {
        if (cache.data is List<*> && cache.data.isEmpty()){
            return RequestResult.Error("Нет интернет соединения")
        }
        return RequestResult.Success(cache.data)
    }

    private fun <E>merge(
        cache: RequestResult.Error<*>,
        server: RequestResult.Success<E>
    ): RequestResult<E> {
        return RequestResult.Success(server.data)
    }

    private fun <E>merge(
        cache: RequestResult.Error<*>,
        server: RequestResult.Error<*>
    ): RequestResult<E> {
        return RequestResult.Error("Нет интернет соединения")
    }

}