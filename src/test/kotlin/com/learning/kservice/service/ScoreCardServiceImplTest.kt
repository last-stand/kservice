package com.learning.kservice.service

import com.learning.kservice.datarepo.ScoreRepository
import com.learning.kservice.datarepo.UserRepository
import com.learning.kservice.models.Score
import com.learning.kservice.models.User
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
internal class ScoreCardServiceImplTest {

    @InjectMocks
    lateinit var scoreService: ScoreCardServiceImpl

    @Mock
    lateinit var scoreRepository: ScoreRepository

    @Mock
    lateinit var userRepository: UserRepository

    @Test
    fun testGetTop3ScoreLeaders() {
        val leaders = ArrayList<Score>()
        given(scoreRepository.findTop3ByOrderByScoreDesc()).willReturn(leaders)

        val topScore = scoreService.leaders()


        assertSame(topScore, leaders)

        verify(scoreRepository, times(1)).findTop3ByOrderByScoreDesc()
    }

    @Test
    fun testAddScore() {
        val user = User("crowny", "crown", "prince", "royal",  LocalDateTime.now())
        val score = Score(user = user, score = 130)
        val optionalUser = Optional.of(user)
        given(userRepository.findById("crowny")).willReturn(optionalUser)
        given(scoreRepository.save(score)).willReturn(score)

        val actualScore = scoreService.addScore(130, "crowny")

        assertSame(score, actualScore)
        verify(userRepository, times(1)).findById("crowny")
        verify(scoreRepository, times(1)).save(score)
    }
}