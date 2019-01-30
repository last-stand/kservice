package com.learning.kservice.service

import com.learning.kservice.datarepo.ScoreRepository
import com.learning.kservice.datarepo.UserRepository
import com.learning.kservice.models.Score
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("scoreCardService")
class ScoreCardServiceImpl : ScoreCardService {

    @Autowired
    lateinit var scoreRepository: ScoreRepository

    @Autowired
    lateinit var userRepository: UserRepository

    override fun leaders(): List<Score> =
            scoreRepository.findTop3ByOrderByScoreDesc()

    override fun addScore(score: Int, userId: String) : Score{
        val user = userRepository.findById(userId).get()
        val newScore = Score(user= user, score = score)
        return scoreRepository.save(newScore)
    }
}