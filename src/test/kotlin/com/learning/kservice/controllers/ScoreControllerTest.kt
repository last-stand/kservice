package com.learning.kservice.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.learning.kservice.models.Score
import com.learning.kservice.models.User
import com.learning.kservice.service.ScoreCardService
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime


@RunWith(MockitoJUnitRunner::class)
class ScoreControllerTest {

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var scoreController: ScoreController

    @Mock
    lateinit var scoreCardService: ScoreCardService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(scoreController).build()
    }


    @Test
    fun testFindTop3Users() {
        val user = User("id1", "john", "doe", "good")
        val score = Score(user = user, score = 130)
        val leadersScore: List<Score> = mutableListOf(score)
        given(scoreCardService.leaders()).willReturn(leadersScore)

        mockMvc.perform(get("/api/leaders/"))
            .andExpect(status().isOk())
            .andDo { result ->
                val scoreJson = result.getResponse().getContentAsString()
                val mapper = jacksonObjectMapper()
                var resultScore: List<Score> = mapper.readValue(scoreJson)
                assertEquals(leadersScore.size, resultScore.size)
                assertEquals(leadersScore, resultScore)
            }
        verify(scoreCardService, times(1)).leaders()
        verifyNoMoreInteractions(scoreCardService)

    }

    @Test
    fun testAddUserScore() {
        val user = User("spidy", "Spider", "Man", "avenger", LocalDateTime.now())
        val score = Score(user = user, score = 147)
        given(scoreCardService.addScore(147, "spidy")).willReturn(score)

        mockMvc.perform(post("/api/leaders/")
                .param("score", "147")
                .param("userId", "spidy")
        ).andExpect(status().isOk)
        .andDo { result ->
            val scoreJson = result.getResponse().getContentAsString()
            val mapper = jacksonObjectMapper()
            var resultScore: Score = mapper.readValue(scoreJson)
            assertEquals(score, resultScore)
        }

        verify(scoreCardService, times(1)).addScore(147, "spidy")
        verifyNoMoreInteractions(scoreCardService)
    }
}
