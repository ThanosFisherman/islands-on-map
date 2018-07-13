package com.advanon.interview.utils

import com.advanon.interview.entities.MapEntity
import com.advanon.interview.entities.Tile

object ArrayUtil {

    fun build2dArray(mapEntity: MapEntity): Array<Array<Tile>> {
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

    private fun sortCoords(mapEntity: MapEntity): List<Tile> {
        val tiles = mapEntity.attributes.tiles.map { it.copy() } //deep copy everything

        return tiles.sortedWith(kotlin.Comparator { o1, o2 ->
            if (compareValues(o1.x, o2.x) == 0) 0 else compareValues(o1.y, o2.y)
        })
    }
}