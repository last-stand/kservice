package com.learning.kservice.service

import com.learning.kservice.models.Score

interface ScoreCardService {
    fun leaders() : List<Score>
    fun addScore(score: Int, userId: String): Score
//    fun updateScore(userId: String, score: Int): Score
}