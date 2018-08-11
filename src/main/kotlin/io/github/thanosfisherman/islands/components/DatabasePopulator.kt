package io.github.thanosfisherman.islands.components

import io.github.thanosfisherman.islands.services.IIslandService
import io.github.thanosfisherman.islands.services.IMapService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabasePopulator(private val restClient: RestClient, private val mapService: IMapService, private val islandService: IIslandService) : CommandLineRunner {

    val log = LoggerFactory.getLogger(this.javaClass)

    override fun run(vararg args: String?) {
        log.info("locating maps and islands ")
        var map = restClient.getMap()
        map = mapService.saveOrUpdateMap(map)
        islandService.locateAndSaveIslands(map)
    }
}