package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.algorithms.IIslandDetector
import io.github.thanosfisherman.islands.entities.IslandEntity
import io.github.thanosfisherman.islands.entities.MapEntity
import io.github.thanosfisherman.islands.utils.findOneById
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Repository
interface IslandRepository : MongoRepository<IslandEntity, String> {
    fun deleteAllByMapId(mapId: String): List<IslandEntity>
}

@Service
class IslandService(private val islandRepository: IslandRepository, private val islandDetector: IIslandDetector) : IIslandService {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun locateAndSaveIslands(mapEntity: MapEntity) {
        deleteAllByMapId(mapEntity)
        val islands = islandDetector.detectIslands(mapEntity)
        islands.onEach { islandRepository.save(it) }
    }

    override fun deleteAllByMapId(mapEntity: MapEntity): List<IslandEntity> {

        val islands = islandRepository.deleteAllByMapId(mapEntity.id ?: "")
        return if (islands.isEmpty()) listOf() else islands

    }

    override fun getAllIslands(): MutableList<IslandEntity> {
        return islandRepository.findAll()
    }

    override fun getIslandById(id: String) = islandRepository.findOneById(id)

}