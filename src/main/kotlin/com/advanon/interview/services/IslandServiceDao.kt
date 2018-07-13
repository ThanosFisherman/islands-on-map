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

    /** using Collection instead of Array makes your live easier. Consider renaming this to "directions".  */
    val coordinates = listOf(
            //Tile(-1, -1, "land"), // left Bottom
            Tile(-1, 0, "land"), // left middle
            //Tile(-1, +1, "land"), // left Top
            Tile(0, -1, "land"), // Bottom
            Tile(0, +1, "land"), // Top
            // Tile(+1, -1, "land"), // right Bottom
            Tile(+1, 0, "land") // right middle
            //Tile(+1, +1, "land")  // right Top
    )

    fun locateIslands(mapEntity: MapEntity): List<IslandEntity> {
        val sortedTiles = sortCoords(mapEntity)
        val neighbors = mutableListOf<Tile>()
        for (tile in sortedTiles) {
            if (tile.type == "water") continue

            log.info("OUTTER TILE " + tile.toString())
            for (tileN in coordinates) {
                val newTile = tile.move(tileN)
                if (sortedTiles.contains(newTile)) {
                    neighbors.add(newTile)

                }
                log.info(newTile.toString())

            }

        }
        return islandRepository.findAll()
    }

    private fun sortCoords(mapEntity: MapEntity): List<Tile> {
        val tiles = mapEntity.attributes.tiles.toMutableList()

        tiles.sortWith(Comparator { o1, o2 ->
            if (compareValues(o1.x, o2.x) == 0) compareValues(o1.x, o2.x) else compareValues(o1.y, o2.y)
        })

        log.info("SIZE ${tiles.size}")

        return tiles
    }

    fun printArray(mapEntity: MapEntity) {

        val strings = build2dArray(mapEntity)
        // Array Value Printing
        for (row in strings) {
            for (j in row)
                print(j)
            println("")
        }
    }

    private fun build2dArray(mapEntity: MapEntity): Array<Array<String>> {
        val tiles = sortCoords(mapEntity)
        val strings = Array(tiles[tiles.size - 1].y) { Array<String>(tiles[tiles.size - 1].x) { "-" } }
        var counter = 0
        for (i in strings.indices)
            for (j in strings[i].indices) {
                strings[i][j] = if (tiles[counter].type == "land") "X" else "-"
                counter++
            }

        return strings
    }
}