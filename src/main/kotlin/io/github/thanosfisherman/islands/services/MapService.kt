package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.entities.MapEntity
import io.github.thanosfisherman.islands.utils.ArrayUtil.build2dArray
import io.github.thanosfisherman.islands.utils.findOneById
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface MapRepository : MongoRepository<MapEntity, String> {
    fun findByDataIdAndDataType(dataId: String, dataType: String): MapEntity?
}

@Service
class MapService(private val mapRepository: MapRepository) : IMapService {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    override fun saveOrUpdateMap(mapEntity: MapEntity): MapEntity {
        val storedMap = mapRepository.findByDataIdAndDataType(mapEntity.data.id, mapEntity.data.type)
        storedMap?.let { mapEntity.id = it.id }
        return mapRepository.save(mapEntity)
    }

    override fun getMaps(): MutableList<MapEntity> = mapRepository.findAll()

    override fun printMaps(): StringBuilder {
        val builder = StringBuilder("<pre>\n\n")
        for (map in getMaps()) {
            val map2d = build2dArray(map.attributes.tiles)

            for (row in map2d) {
                for (j in row)
                    if (j.isLand) builder.append("X") else builder.append("-")
                builder.append("\n")
            }
            builder.append("\n")
        }
        return builder.append("</pre>\n")
    }

    override fun findMapById(mapId: String): MapEntity? = mapRepository.findOneById(mapId)
}