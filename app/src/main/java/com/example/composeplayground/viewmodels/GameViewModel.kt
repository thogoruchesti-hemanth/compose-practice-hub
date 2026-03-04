package com.example.composeplayground.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeplayground.data.Alien
import com.example.composeplayground.data.Bullet
import com.example.composeplayground.data.GameUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {

    var uiState by mutableStateOf(GameUiState())
        private set

    private val screenWidth = 1000f
    private val screenHeight = 1800f

    private var alienDirection = 5f

    init {
        startGame()
    }

    private fun startGame() {
        uiState = uiState.copy(
            aliens = List(5) { index ->
                Alien(
                    id = index,
                    x = 150f * index + 100,
                    y = 150f
                )
            }
        )
        gameLoop()
    }

    private fun gameLoop() {
        viewModelScope.launch {
            while (!uiState.gameOver) {
                delay(16L)
                updateGame()
            }
        }
    }

    private fun updateGame() {

        // Move Aliens
        val movedAliens = uiState.aliens.map { alien ->
            if (!alien.alive) alien
            else alien.copy(x = alien.x + alienDirection)
        }

        // Change direction at screen edges
        if (movedAliens.any { it.x > screenWidth - 120 || it.x < 0 }) {
            alienDirection *= -1
        }

        // Move Bullet
        val updatedBullet =
            if (uiState.bullet.active)
                uiState.bullet.copy(y = uiState.bullet.y - 25f)
            else uiState.bullet

        // Check collision
        var score = uiState.score
        val aliensAfterHit = movedAliens.map { alien ->
            if (
                alien.alive &&
                updatedBullet.active &&
                updatedBullet.x in alien.x..(alien.x + 100) &&
                updatedBullet.y in alien.y..(alien.y + 100)
            ) {
                score += 10
                alien.copy(alive = false)
            } else alien
        }

        val bulletStillActive = updatedBullet.y > 0 &&
                aliensAfterHit.none { !it.alive }

        uiState = uiState.copy(
            aliens = aliensAfterHit,
            bullet = updatedBullet.copy(active = bulletStillActive),
            score = score,
            gameOver = aliensAfterHit.all { !it.alive }
        )
    }

    fun movePlayer(delta: Float) {
        val newX = (uiState.playerX + delta).coerceIn(0f, screenWidth - 120)
        uiState = uiState.copy(playerX = newX)
    }

    fun shoot() {
        if (!uiState.bullet.active) {
            uiState = uiState.copy(
                bullet = Bullet(
                    x = uiState.playerX + 40,
                    y = screenHeight - 250,
                    active = true
                )
            )
        }
    }

    fun restart() {
        alienDirection = 5f
        uiState = GameUiState()
        startGame()
    }
}