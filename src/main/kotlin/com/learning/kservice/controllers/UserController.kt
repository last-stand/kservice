package com.learning.kservice.controllers

import com.learning.kservice.datarepo.UserRepository
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3000)
@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) = repository.findById(login)

    @GetMapping("/desc")
    fun findAllByDesc() = repository.findAllByOrderByAddedAtDesc()
}