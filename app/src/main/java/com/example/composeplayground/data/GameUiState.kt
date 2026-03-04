package com.example.composeplayground.data

data class GameUiState(
    val aliens: List<Alien> = emptyList(),
    val bullet: Bullet = Bullet(0f, 0f, false),
    val playerX: Float = 500f,
    val score: Int = 0,
    val gameOver: Boolean = false
)
