package com.tapdeveloper.themoviedb.data.remote.api

/**
 * This class manage all the logic of a generic Paginator. Its contains lambdas of all the steps, ej:
 * [onLoadUpdated]----[onRequest]---------------------[onSuccess]---------[getNextKey]----[onLoadUpdated]
 * Loading   -> Make request with page x -> Provide success response -> set next page -> stop loading
 * [onLoadUpdated]----[onRequest]---------------------[onError]--------[onLoadUpdated]
 * Loading   -> Make request with page x -> Provide error response -> stop loading.
 * When implemented, you just need to specify what to do on each step in the corresponding lambda,
 * this class will do the rest.
 * */
class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest)
            return
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        currentKey = getNextKey(items)
        onSuccess(items, currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}
