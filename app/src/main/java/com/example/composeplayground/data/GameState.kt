package com.example.composeplayground.data

data class GameState(
    val alienX: Float = 0f,
    val alienDirection: Float = 5f,
    val bulletY: Float? = null,
    val score: Int = 0
)
