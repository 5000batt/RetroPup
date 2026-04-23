package com.kjw.retropup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {

    // UI가 구독할 '단 하나의' 통신 상태 (초기값은 Loading)
    private val _dogState = MutableStateFlow<Resource<DogResponse>>(Resource.Loading)
    val dogState: StateFlow<Resource<DogResponse>> = _dogState

    fun fetchRandomDog() {
        viewModelScope.launch {
            // 1. 통신 시작 전 '로딩 상태' 방출 (명령이 아닌 상태 변경!)
            _dogState.value = Resource.Loading

            // 2. safeApiCall을 통해 안전하게 통신 결과 받아오기
            val result = safeApiCall { RetrofitClient.dogApi.getRandomDog() }

            // 3. 통신 결과(Success or Error) 방출
            _dogState.value = result
        }
    }
}