package com.tapdeveloper.themoviedb.data.remote.api

interface Paginator<Key, Item> {

    suspend fun loadNextItems()

    fun reset()
}