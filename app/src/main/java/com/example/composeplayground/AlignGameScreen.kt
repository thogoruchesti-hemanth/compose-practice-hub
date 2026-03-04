package com.example.composeplayground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeplayground.viewmodels.GameViewModel

@Composable
fun AlienGameScreen(
    viewModel: GameViewModel = viewModel()
) {

    val state = viewModel.uiState

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Aliens
        state.aliens.forEach { alien ->
            if (alien.alive) {
                AndroidAlien(
                    color = Color.Green,
                    modifier = Modifier
                        .offset(alien.x.dp, alien.y.dp)
                        .size(100.dp)
                )
            }
        }

        // Player
        AndroidAlien(
            color = Color.Cyan,
            modifier = Modifier
                .offset(state.playerX.dp, 1600.dp)
                .size(100.dp)
        )

        // Bullet
        if (state.bullet.active) {
            Box(
                modifier = Modifier
                    .offset(
                        state.bullet.x.dp,
                        state.bullet.y.dp
                    )
                    .size(12.dp, 35.dp)
                    .background(Color.Red)
            )
        }

        // Score
        Text(
            text = "Score: ${state.score}",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )

        // Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Button(onClick = { viewModel.movePlayer(-50f) }) {
                Text("LEFT")
            }
            Button(onClick = { viewModel.shoot() }) {
                Text("FIRE")
            }
            Button(onClick = { viewModel.movePlayer(50f) }) {
                Text("RIGHT")
            }
        }

        if (state.gameOver) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(Color.Gray.copy(.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "YOU WIN 👾",
                        color = Color.White
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { viewModel.restart() }) {
                        Text("RESTART")
                    }
                }
            }
        }
    }
}