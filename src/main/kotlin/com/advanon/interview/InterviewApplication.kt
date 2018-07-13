package com.advanon.interview

import com.advanon.interview.clients.RestClient
import com.advanon.interview.services.IslandServiceDao
import com.advanon.interview.services.MapServiceDao
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class InterviewApplication {

    val log = LoggerFactory.getLogger(this.javaClass)

    @Bean
    fun init(restClient: RestClient, mapServiceDao: MapServiceDao, islandServiceDao: IslandServiceDao) = CommandLineRunner {
        val map = restClient.getMap()
        mapServiceDao.saveOrUpdateMap(map)
        // islandServiceDao.locateIslands(map)
        islandServiceDao.printArray(map)
    }
}


fun main(args: Array<String>) {
    runApplication<InterviewApplication>(*args)
}
