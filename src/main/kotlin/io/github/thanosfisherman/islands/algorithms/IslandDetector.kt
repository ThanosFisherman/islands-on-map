package io.github.thanosfisherman.islands.algorithms

import io.github.thanosfisherman.islands.entities.IslandEntity
import io.github.thanosfisherman.islands.entities.MapEntity
import io.github.thanosfisherman.islands.entities.Tile
import io.github.thanosfisherman.islands.utils.ArrayUtil.build2dArray
import org.springframework.stereotype.Component
import kotlin.streams.toList

@Component
class IslandDetector : IIslandDetector {

    override fun detectIslands(mapEntity: MapEntity): List<IslandEntity> {
        val map2d = build2dArray(mapEntity)
        val islands = mutableListOf<IslandEntity>()


        fun changeLandToWater(map2d: Array<Array<Tile>>, i: Int, j: Int, tiles: MutableList<Tile>) {

            if (i >= 0 && i < map2d.size && j >= 0 && j < map2d[0].size && map2d[i][j].isLand) {
                map2d[i][j].type = "water"
                tiles.add(map2d[i][j])
                changeLandToWater(map2d, i - 1, j, tiles)
                changeLandToWater(map2d, i + 1, j, tiles)
                changeLandToWater(map2d, i, j - 1, tiles)
                changeLandToWater(map2d, i, j + 1, tiles)
            }
        }

        for (i in map2d.indices)
            for (j in map2d[i].indices)
                if (map2d[i][j].isLand) {
                    val tiles = mutableListOf<Tile>()
                    changeLandToWater(map2d, i, j, tiles)
                    mapEntity.id?.let { islands.add(IslandEntity(tiles, it)) }
                }

        return islands.stream().peek { t -> t.tiles.onEach { it.type = "land" } }.toList()
    }
}