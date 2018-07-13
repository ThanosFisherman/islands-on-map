package com.advanon.interview.services

import com.advanon.interview.entities.IslandEntity
import com.advanon.interview.entities.MapEntity
import com.advanon.interview.entities.Tile
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*


@Repository
interface IslandRepository : MongoRepository<IslandEntity, String>

@Service
class IslandServiceDao(private val islandRepository: IslandRepository) {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun locateIslands(mapEntity: MapEntity) {
        val map2d = build2dArray(mapEntity)

        var counter = 0

        for (i in map2d.indices)
            for (j in map2d[i].indices) {
                if (map2d[i][j].type == "land") {
                    counter++
                    changeLandToWater(map2d, i, j)
                }
            }
        log.info("NUMBER OF ISLANDS $counter")
    }

    private fun changeLandToWater(map2d: Array<Array<Tile>>, i: Int, j: Int) {
        if (i < 0 || i >= map2d.size || j < 0 || j >= map2d[0].size || map2d[i][j].type == "water") return
        map2d[i][j].type = "water"
        changeLandToWater(map2d, i - 1, j)
        changeLandToWater(map2d, i + 1, j)
        changeLandToWater(map2d, i, j - 1)
        changeLandToWater(map2d, i, j + 1)
    }

    private fun sortCoords(mapEntity: MapEntity): List<Tile> {
        val tiles = mapEntity.attributes.tiles.toMutableList()

        tiles.sortWith(Comparator { o1, o2 ->
            if (compareValues(o1.x, o2.x) == 0) compareValues(o1.x, o2.x) else compareValues(o1.y, o2.y)
        })

        return tiles
    }

    fun printArray(mapEntity: MapEntity) {

        val map2d = build2dArray(mapEntity)
        // Array Value Printing
        for (row in map2d) {
            for (j in row)
                print(if (j.type == "land") "X" else "-")
            println("")
        }
    }

    private fun build2dArray(mapEntity: MapEntity): Array<Array<Tile>> {
        val tiles = sortCoords(mapEntity)
        val tiles2d = Array(tiles[tiles.size - 1].y) { Array(tiles[tiles.size - 1].x) { Tile(0, 0, "water") } }
        var counter = 0
        for (i in tiles2d.indices)
            for (j in tiles2d[i].indices) {
                tiles2d[i][j] = tiles[counter]
                counter++
            }

        return tiles2d
    }
}