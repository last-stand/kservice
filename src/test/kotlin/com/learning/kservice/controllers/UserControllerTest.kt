package com.learning.kservice.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.learning.kservice.datarepo.UserRepository
import com.learning.kservice.models.User
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDateTime
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class UserControllerTest {

    lateinit var mockMvc: MockMvc

    @InjectMocks
    lateinit var userController: UserController

    @Mock
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }


    @Test
    fun testFindAllUsers() {
        val user = User("id1", "john", "doe", "good", LocalDateTime.now())
        val userList: List<User> = mutableListOf(user)
        given(userRepository.findAll()).willReturn(userList)

        mockMvc.perform(get("/api/user/"))
            .andExpect(status().isOk())
            .andDo { result ->
                val userJson = result.getResponse().getContentAsString()
                val mapper = jacksonObjectMapper()
                val resultUserList: List<User> = mapper.readValue(userJson)
                assertEquals(userList, resultUserList)
            }
        verify(userRepository, times(1)).findAll()
    }

    @Test
    fun findOne() {
        val user = User("id1", "john", "doe", "good", LocalDateTime.now())
        val optionalUser = Optional.of(user)
        given(userRepository.findById("id1")).willReturn(optionalUser)

        mockMvc.perform(get("/api/user/id1"))
                .andExpect(status().isOk())
                .andDo { result ->
                    val userJson = result.getResponse().getContentAsString()
                    val mapper = jacksonObjectMapper()
                    val resultUser: User = mapper.readValue(userJson)
                    assertEquals(user, resultUser)
                }
        verify(userRepository, times(1)).findById("id1")
    }
}
