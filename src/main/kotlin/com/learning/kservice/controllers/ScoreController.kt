package com.learning.kservice.controllers

import com.learning.kservice.service.ScoreCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = arrayOf("http://localhost:3000"), maxAge = 3000)
@RestController
@RequestMapping("/api/leaders")
class ScoreController {

    @Autowired
    lateinit var scoreCardService: ScoreCardService

    @GetMapping("/")
    fun findTop3() = scoreCardService.leaders()

    @PostMapping("/")
    fun addScore(@RequestParam("score") score: Int, @RequestParam("userId") userId: String) =
            scoreCardService.addScore(score, userId)
}
