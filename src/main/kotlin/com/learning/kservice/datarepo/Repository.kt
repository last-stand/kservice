package com.learning.kservice.datarepo

import com.learning.kservice.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String> {
    fun findAllByOrderByAddedAtDesc(): Iterable<User>
}