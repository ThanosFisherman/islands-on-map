package com.advanon.interview

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class InterviewApplication

fun main(args: Array<String>) {
    runApplication<InterviewApplication>(*args)
}