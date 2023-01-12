package com.tapdeveloper.themoviedb.data.repository

import okhttp3.Interceptor
import okhttp3.Response

/** Will add the api key so no need to added on every call */
class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val url = original.url.newBuilder().addQueryParameter("api_key", apiKey).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }
}
