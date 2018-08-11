package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.entities.MapEntity

interface IMapService {
    fun saveOrUpdateMap(mapEntity: MapEntity): MapEntity
    fun getMaps(): List<MapEntity>
    fun printMaps(): StringBuilder
    fun findMapById(mapId: String): MapEntity?
}