package com.learning.kservice.controllers

import com.learning.kservice.models.Score
import com.learning.kservice.models.User
import com.learning.kservice.service.ScoreCardService
import org.hamcrest.Matchers.*
import org.hamcrest.collection.IsCollectionWithSize.hasSize
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
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
        val user1 = User("id1", "john", "doe", "good", LocalDateTime.now())
        val user2 = User("id2", "ram", "ji", "nice", LocalDateTime.now())
        val user3 = User("id3", "bat", "man", "ok", LocalDateTime.now())
        val score1 = Score(user = user1, score = 130)
        val score2 = Score(user = user2, score = 101)
        val score3 = Score(user = user3, score = 99)
        val leaders: List<Score> = mutableListOf(score1, score2, score3)
        given(scoreCardService.leaders()).willReturn(leaders)

        mockMvc.perform(get("/api/leaders/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize<Any>(3)))
                .andExpect(jsonPath("$[0].score", equalTo(130)))
                .andExpect(jsonPath("$[1].score", equalTo(101)))
                .andExpect(jsonPath("$[2].score", equalTo(99)))
                .andExpect(jsonPath("$[0].user.login", equalTo("id1")))
                .andExpect(jsonPath("$[1].user.login", equalTo("id2")))
                .andExpect(jsonPath("$[2].user.login", equalTo("id3")))

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
                .andExpect(jsonPath("$.score", equalTo(147)))
                .andExpect(jsonPath("$.user.login", equalTo("spidy")))
                .andExpect(jsonPath("$.user.firstname", equalTo("Spider")))
                .andExpect(jsonPath("$.user.lastname", equalTo("Man")))
                .andExpect(jsonPath("$.user.description", equalTo("avenger")))

        verify(scoreCardService, times(1)).addScore(147, "spidy")
        verifyNoMoreInteractions(scoreCardService)
    }
}
