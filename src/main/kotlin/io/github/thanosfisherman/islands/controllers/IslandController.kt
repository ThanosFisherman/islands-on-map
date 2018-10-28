package io.github.thanosfisherman.islands.controllers

import io.github.thanosfisherman.islands.components.RestClient
import io.github.thanosfisherman.islands.entities.MapEntity
import io.github.thanosfisherman.islands.services.IIslandService
import io.github.thanosfisherman.islands.services.IMapService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class IslandController(private val islandService: IIslandService, private val mapService: IMapService, private val restClient: RestClient) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("/maps")
    fun populateMapsAndIslands(): ResponseEntity<MapEntity> {
        var map = restClient.getMap()
        map = mapService.saveOrUpdateMap(map)
        islandService.locateAndSaveIslands(map)
        return ResponseEntity.ok(map)
    }

    @GetMapping("/islands")
    fun getAllIslands() = ResponseEntity.ok(islandService.getAllIslands())

    @GetMapping("/islands/{id}")
    fun getIslandById(@PathVariable(value = "id", required = true) islandId: String): ResponseEntity<Any> {
        log.info(islandId)
        val island = islandService.getIslandById(islandId)
                ?: return ResponseEntity.badRequest().body(mapOf("error" to "No such id"))

        return ResponseEntity.ok(island)

    }

    @DeleteMapping("islands/{id}")
    fun deleteIslandsOnMap(@PathVariable(value = "id", required = true) mapId: String): ResponseEntity<Any> {
        log.info(mapId)
        val mapEntity = mapService.findMapById(mapId)
                ?: return ResponseEntity.badRequest().body(mapOf("error" to "No Map that matches this id"))
        return ResponseEntity.ok(islandService.deleteAllByMapId(mapEntity))

    }

    @GetMapping("/maps")
    fun getMaps() = ResponseEntity.ok(mapService.getMaps())

    @GetMapping("/maps/ascii")
    fun printMap(): String {
        return mapService.printMaps().toString()
    }
}