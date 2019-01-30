package com.learning.kservice.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class User(
        @Id val login: String,
        val firstname: String,
        val lastname: String,
        val description: String? = null,

        @JsonDeserialize(using=LocalDateTimeDeserializer::class)
        val addedAt: LocalDateTime = LocalDateTime.now())
