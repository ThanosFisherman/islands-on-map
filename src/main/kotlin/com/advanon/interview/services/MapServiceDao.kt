package com.advanon.interview.services

import com.advanon.interview.entities.MapEntity
import com.advanon.interview.utils.ArrayUtil.build2dArray
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
class MapServiceDao(private val mapRepository: MapRepository) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun saveOrUpdateMap(mapEntity: MapEntity): MapEntity {
        val storedMap = mapRepository.findByDataIdAndDataType(mapEntity.data.id, mapEntity.data.type)
        storedMap?.let { mapEntity.id = it.id }
        return mapRepository.save(mapEntity)
    }

    fun getMaps() = mapRepository.findAll()

    fun printMaps(): StringBuilder {

        val builder = StringBuilder("<pre>\n\n")
        for (map in getMaps()) {
            val map2d = build2dArray(map)

            for (row in map2d) {
                for (j in row)
                    if (j.type == "land") builder.append("X") else builder.append("-")
                builder.append("\n")
            }
            builder.append("\n")
        }
        return builder.append("</pre>\n")
    }
}