package com.learning.kservice.datarepo

import com.learning.kservice.models.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findAllByOrderByAddedAtDesc(): Iterable<User>
}