package com.learning.kservice.datarepo

import com.learning.kservice.models.Score
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScoreRepository : CrudRepository<Score, String> {
    fun findTop3ByOrderByScoreDesc(): List<Score>
}