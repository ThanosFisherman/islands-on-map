package com.advanon.interview.controllers

import com.advanon.interview.clients.RestClient
import com.advanon.interview.entities.MapEntity
import com.advanon.interview.services.IslandServiceDao
import com.advanon.interview.services.MapServiceDao
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class IslandController(private val islandServiceDao: IslandServiceDao, private val mapServiceDao: MapServiceDao, private val restClient: RestClient) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/maps")
    fun populateMapsAndIslands(): ResponseEntity<MapEntity> {
        var map = restClient.getMap()
        map = mapServiceDao.saveOrUpdateMap(map)
        islandServiceDao.locateAndSaveIslands(map)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/islands")
    fun getAllIslands() = ResponseEntity.ok(islandServiceDao.getAllIslands())

    @GetMapping("/islands/{id}")
    fun getIslandById(@PathVariable(value = "id", required = true) islandId: String): ResponseEntity<Any> {
        log.info(islandId)
        val island = islandServiceDao.getIslandById(islandId)
                ?: return ResponseEntity.badRequest().body(mapOf("error" to "no such id"))

        return ResponseEntity.ok(island)

    }

    @GetMapping("/maps")
    fun getMaps() = ResponseEntity.ok(mapServiceDao.getMaps())

    @GetMapping("/maps/ascii")
    fun printMap(): String {
        return mapServiceDao.printMaps().toString()
    }
}