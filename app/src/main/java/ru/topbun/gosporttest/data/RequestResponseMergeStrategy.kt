package ru.topbun.gosporttest.data

import ru.topbun.gosporttest.domain.RequestResult
import javax.inject.Inject

interface MergeStrategy {
    fun merge(cache: RequestResult, server: RequestResult): RequestResult
}

class RequestResponseMergeStrategy @Inject constructor(): MergeStrategy{

    override fun merge(cache: RequestResult, server: RequestResult): RequestResult {
        return when{
            cache is RequestResult.InProgress<*> && server is RequestResult.InProgress<*> ->
                merge(cache, server)
            cache is RequestResult.Success<*> && server is RequestResult.InProgress<*> ->
                merge(cache, server)
            cache is RequestResult.InProgress<*> && server is RequestResult.Success<*> ->
                merge(cache, server)
            cache is RequestResult.InProgress<*> && server is RequestResult.Error ->
                merge(cache, server)
            else -> error("Unimplemented branch")
        }
    }

    private fun merge(
        cache: RequestResult.InProgress<*>,
        server: RequestResult.InProgress<*>
    ): RequestResult {
        return when {
            server.data != null -> RequestResult.InProgress(server.data)
            else -> RequestResult.InProgress(cache.data)
        }
    }

    private fun merge(
        cache: RequestResult.Success<*>,
        server: RequestResult.InProgress<*>
    ): RequestResult {
        return RequestResult.InProgress(cache.data)
    }

    private fun merge(
        cache: RequestResult.InProgress<*>,
        server: RequestResult.Success<*>
    ): RequestResult {
        return RequestResult.InProgress(server.data)
    }

    private fun merge(
        cache: RequestResult.InProgress<*>,
        server: RequestResult.Error
    ): RequestResult {
        return RequestResult.InProgress(cache.data)
    }

}