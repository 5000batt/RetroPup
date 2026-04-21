package com.kjw.retropup

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // 1. 클라이언트가 원래 보내려던 Request를 가로챕니다.
        val originalRequest = chain.request()

        // 2. 보안 저장소에서 유효한 Access Token을 가져옵니다.
        val accessToken = TokenManager.getAccessToken()
        val appVersion = "1.0.0"

        // 3. 기존 Request를 복제(newBuilder)하면서 공통 Header를 주입합니다.
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("App-Version", appVersion)
            .build()

        // 4. 조립이 완료된 새로운 Request를 서버로 전송합니다.
        return chain.proceed(newRequest)
    }
}