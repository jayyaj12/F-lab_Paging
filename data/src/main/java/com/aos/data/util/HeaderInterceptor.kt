package com.aos.data.util

import com.aos.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        // Auth 헤더 처리
        builder.addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }
}