package com.advanon.interview.services

import com.advanon.interview.entities.IslandEntity
import com.advanon.interview.entities.MapEntity
import com.advanon.interview.entities.Tile
import com.advanon.interview.utils.ArrayUtil.build2dArray
import com.advanon.interview.utils.findOneById
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Repository
interface IslandRepository : MongoRepository<IslandEntity, String> {
    fun deleteAllByMapId(mapId: String)
}

@Service
class IslandServiceDao(private val islandRepository: IslandRepository, private val mapServiceDao: MapServiceDao) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun locateAndSaveIslands(mapEntity: MapEntity) {
        val map2d = build2dArray(mapEntity)

        deleteAllByMapId(mapEntity)
        for (i in map2d.indices)
            for (j in map2d[i].indices) {
                if (map2d[i][j].type == "land") {
                    val tiles = mutableListOf<Tile>()
                    changeLandToWater(map2d, i, j, tiles)
                    saveIsland(mapEntity, tiles)
                }
            }
    }

    private fun changeLandToWater(map2d: Array<Array<Tile>>, i: Int, j: Int, tiles: MutableList<Tile>) {

        if (i < 0 || i >= map2d.size || j < 0 || j >= map2d[0].size || map2d[i][j].type == "water") return
        tiles.add(map2d[i][j])
        map2d[i][j].type = "water"
        changeLandToWater(map2d, i - 1, j, tiles)
        changeLandToWater(map2d, i + 1, j, tiles)
        changeLandToWater(map2d, i, j - 1, tiles)
        changeLandToWater(map2d, i, j + 1, tiles)
    }

    fun deleteAllByMapId(mapEntity: MapEntity) {
        mapEntity.id?.let { islandRepository.deleteAllByMapId(it) }
    }

    fun getAllIslands(): MutableList<IslandEntity> {
        val maps = mapServiceDao.getMaps()
        for (map in maps)
            locateAndSaveIslands(map)
        return islandRepository.findAll()
    }

    fun getIslandById(id: String) = islandRepository.findOneById(id)

    private fun saveIsland(mapEntity: MapEntity, tiles: List<Tile>) {

        val island = mapEntity.id?.let { IslandEntity(tiles, it) }
        island?.let { islandRepository.save(it) }

    }

}