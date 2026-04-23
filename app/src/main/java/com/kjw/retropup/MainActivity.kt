package com.kjw.retropup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

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
fun DogScreen(viewModel: DogViewModel = viewModel()) {
    // ViewModel의 StateFlow를 Compose State로 관찰(Collect)
    val dogState by viewModel.dogState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 컴파일러가 모든 분기 처리를 강제하므로 휴먼 에러 원천 차단
        when (val state = dogState) {
            is Resource.Loading -> {
                CircularProgressIndicator() // 로딩 스피너
            }
            is Resource.Success -> {
                AsyncImage(
                    model = state.data.imageUrl,
                    contentDescription = "랜덤 강아지 사진",
                    modifier = Modifier.size(300.dp)
                )
            }
            is Resource.Error -> {
                Text(
                    text = state.message, // 가공해둔 에러 메시지 출력
                    color = Color.Red,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.fetchRandomDog() }) {
            Text("강아지 불러오기")
        }
    }
}
