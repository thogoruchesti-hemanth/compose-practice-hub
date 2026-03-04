package com.example.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.composeplayground.data.GameState
import com.example.composeplayground.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
//                AlienGame()
                AlienGameScreen()
            }
        }
    }
}


@Composable
fun AndroidAlien(
    color: Color,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.android_alien),
        contentDescription = null,
        colorFilter = ColorFilter.tint(color = color)
    )
}

@Composable
fun AlienGame() {

    var gameState by remember { mutableStateOf(GameState()) }

    val screenWidth = 800f

    // Game loop
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(16L)

            // Move alien
            val newX = gameState.alienX + gameState.alienDirection
            var newDirection = gameState.alienDirection

            if (newX > screenWidth - 150 || newX < 0) {
                newDirection *= -1
            }

            // Move bullet
            val newBulletY = gameState.bulletY?.minus(20f)

            // Collision detection
            val hit = newBulletY != null &&
                    newBulletY < 200 &&
                    newX in 0f..screenWidth

            gameState = gameState.copy(
                alienX = newX,
                alienDirection = newDirection,
                bulletY = if (newBulletY != null && newBulletY > 0) newBulletY else null,
                score = if (hit) gameState.score + 1 else gameState.score
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        // Alien
        AndroidAlien(
            color = Color.Green,
            modifier = Modifier
                .offset(x = gameState.alienX.dp, y = 100.dp)
                .size(100.dp)
        )

        // Bullet
        gameState.bulletY?.let {
            Box(
                modifier = Modifier
                    .offset(x = 200.dp, y = it.dp)
                    .size(10.dp, 30.dp)
                    .background(Color.Red)
            )
        }

        // Fire Button
        Button(
            onClick = {
                if (gameState.bulletY == null) {
                    gameState = gameState.copy(bulletY = 1200f)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("FIRE")
        }

        // Score
        Text(
            text = "Score: ${gameState.score}",
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(16.dp)
        )
    }
}

