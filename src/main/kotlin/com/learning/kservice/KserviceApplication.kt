package com.learning.kservice

import com.learning.kservice.datarepo.UserRepository
import com.learning.kservice.models.User
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class KserviceApplication {

    @Bean
    fun databaseInitializer(userRepository: UserRepository) = CommandLineRunner {
        val jd = User("jonny", "Jonny", "Doe")
        userRepository.save(jd)

        val sherman = User("sherman", "Sherman", "F.")
        userRepository.save(sherman)
    }
}

fun main(args: Array<String>) {
    runApplication<KserviceApplication>(*args)
}

