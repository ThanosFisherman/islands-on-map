package io.github.thanosfisherman.islands.algorithms

import io.github.thanosfisherman.islands.entities.IslandEntity
import io.github.thanosfisherman.islands.entities.MapEntity

interface IIslandDetector {
    fun detectIslands(mapEntity: MapEntity): List<IslandEntity>
}