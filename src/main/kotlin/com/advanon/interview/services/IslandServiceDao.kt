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
import java.util.stream.Collectors


@Repository
interface IslandRepository : MongoRepository<IslandEntity, String>

@Service
class IslandServiceDao(private val islandRepository: IslandRepository) {
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /*fun locateIslands(mapEntity: MapEntity): IslandEntity {
        for (tile in mapEntity.attributes.tiles)
            if (tile.type == "water") continue

    }*/

    fun sortCoords(mapEntity: MapEntity): List<Tile> {
        val tiles = mapEntity.attributes.tiles.toMutableList()

        tiles.sortWith(Comparator { o1, o2 ->
            if (compareValues(o1.x, o2.x) != 0) compareValues(o1.x, o2.x) else compareValues(o1.y, o2.y)
        })

        log.info("SIZE ${tiles.size} CONTENT $tiles")
        return tiles
    }

    fun printArray(mapEntity: MapEntity) {
        val tiles = sortCoords(mapEntity)
        for (i in tiles.indices) {
            if ((i) % 5 == 0)
                print("\n")
            print(if (tiles[i].type == "land") "x" else "-")
        }
        print("\n")
    }

    fun getNeighbors() {

        val result = getNearCoordinates(Tile(5, 4, "land"))
        log.info(result.toString())
    }

    /** using Collection instead of Array makes your live easier. Consider renaming this to "directions".  */
    var coordinates = listOf(
            //Tile(-1, -1, "land"), // left Bottom
            Tile(-1, 0, "land"), // left middle
            //Tile(-1, +1, "land"), // left Top
            Tile(0, -1, "land"), // Bottom
            Tile(0, +1, "land"), // Top
            // Tile(+1, -1, "land"), // right Bottom
            Tile(+1, 0, "land") // right middle
            //Tile(+1, +1, "land")  // right Top
    )

    /** @return a collection of eight nearest coordinates near origin
     */
    fun getNearCoordinates(origin: Tile): Collection<Tile> {
        return coordinates.stream().map(origin::move).collect(Collectors.toList())

    }
}