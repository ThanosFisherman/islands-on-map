package io.github.thanosfisherman.islands.utils

import io.github.thanosfisherman.islands.entities.Tile
import io.github.thanosfisherman.islands.entities.TileType

object ArrayUtil {

    fun build2dArray(tiles: List<Tile>): Array<Array<Tile>> {

        //sort the coords first
        val tilesSorted = tiles.map { it.copy() }
                .sortedWith(Comparator { o1, o2 -> if (compareValues(o1.x, o2.x) == 0) 0 else compareValues(o1.y, o2.y) })

        //create a 2d sea
        val tiles2d = Array(tilesSorted[tilesSorted.size - 1].x + 1) { i -> Array(tilesSorted[tilesSorted.size - 1].y + 1) { j -> Tile(i, j, TileType.WATER) } }

        //add islands to 2d sea
        tilesSorted.forEach { t -> tiles2d[t.x][t.y] = t }

        return tiles2d
    }
}