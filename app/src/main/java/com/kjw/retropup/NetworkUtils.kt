package com.kjw.retropup

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall()
            Resource.Success(response)
        } catch (e: HttpException) {
            Resource.Error("서버 에러가 발생했습니다. (코드: ${e.code()})", e)
        } catch (e: IOException) {
            Resource.Error("네트워크 연결이 불안정합니다. 와이파이나 데이터를 확인해주세요.", e)
        } catch (e: Exception) {
            Resource.Error("알 수 없는 오류가 발생했습니다: ${e.message}", e)
        }
    }
}