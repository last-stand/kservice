package com.learning.kservice.models

import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.persistence.GenerationType
import javax.persistence.GeneratedValue

@Entity
data class Score(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,

        @OneToOne
        @JoinColumn(name = "login")
        val user: User,

        val score: Int = 0)