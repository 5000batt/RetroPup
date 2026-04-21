package com.kjw.retropup

object TokenManager {
    // 테스트를 위해 임의의 가짜 토큰 값을 넣어둡니다.
    private var accessToken: String? = "mock_access_token_12345"

    /**
     * 저장된 Access Token을 반환합니다.
     * 토큰이 없으면 빈 문자열을 반환합니다.
     */
    fun getAccessToken(): String {
        return accessToken ?: ""
    }

    /**
     * (참고용) 나중에 로그인을 구현한다면, 서버에서 받은 새 토큰을
     * 저장할 때 이 함수를 사용하게 됩니다.
     */
    fun saveToken(newToken: String) {
        accessToken = newToken
    }

    /**
     * (참고용) 로그아웃 시 토큰을 삭제할 때 사용합니다.
     */
    fun clearToken() {
        accessToken = null
    }
}