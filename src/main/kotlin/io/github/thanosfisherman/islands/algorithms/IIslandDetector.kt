package io.github.thanosfisherman.islands.algorithms

import io.github.thanosfisherman.islands.entities.IslandEntity
import io.github.thanosfisherman.islands.entities.Tile

interface IIslandDetector {
    fun detectIslands(mapId: String, tiles: List<Tile>): List<IslandEntity>
}