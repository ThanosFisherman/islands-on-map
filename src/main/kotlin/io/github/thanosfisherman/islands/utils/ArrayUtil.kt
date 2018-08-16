package io.github.thanosfisherman.islands.utils

import io.github.thanosfisherman.islands.entities.Tile

object ArrayUtil {

    fun build2dArray(tiles: List<Tile>): Array<Array<Tile>> {
        val tilesSorted = tiles.map { it.copy() }
                .sortedWith(Comparator { o1, o2 -> if (compareValues(o1.x, o2.x) == 0) 0 else compareValues(o1.y, o2.y) })
        val tiles2d = Array(tilesSorted[tilesSorted.size - 1].y) { _ -> Array(tilesSorted[tilesSorted.size - 1].x) { Tile(0, 0, "water") } }
        var counter = 0
        for (i in tiles2d.indices)
            for (j in tiles2d[i].indices) {
                tiles2d[i][j] = tiles[counter]
                counter++
            }

        return tiles2d
    }
}