package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.entities.IslandEntity
import io.github.thanosfisherman.islands.entities.MapEntity

interface IIslandService {
    fun locateAndSaveIslands(mapEntity: MapEntity)
    fun deleteAllByMapId(mapEntity: MapEntity): List<IslandEntity>
    fun getAllIslands(): MutableList<IslandEntity>
    fun getIslandById(id: String): IslandEntity?
}