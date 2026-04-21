package com.kjw.retropup

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogScreen()
        }
    }
}

@Composable
fun DogScreen() {
    // 강아지 사진 URL을 저장할 상태값
    var dogImageUrl by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = dogImageUrl,
            contentDescription = "랜덤 강아지 사진",
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp)
        )

        Button(onClick = {
            // 코루틴 스코프를 열어 백그라운드 환경에서 안전하게 통신 시작
            coroutineScope.launch {
                try {
                    val response = RetrofitClient.dogApi.getRandomDog()
                    dogImageUrl = response.imageUrl
                    Log.d("RetroPup", "통신 성공: ${response.imageUrl}")
                } catch (e: Exception) {
                    Log.e("RetroPup", "통신 실패: ${e.message}")
                }
            }
        }) {
            Text("강아지 불러오기")
        }
    }
}
