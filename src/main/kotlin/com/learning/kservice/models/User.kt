package com.learning.kservice.models

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        @Id val login: String,
        val firstname: String,
        val lastname: String,
        val description: String? = null,
        val addedAt: LocalDateTime = LocalDateTime.now())