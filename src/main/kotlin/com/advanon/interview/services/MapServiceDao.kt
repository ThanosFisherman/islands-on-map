package com.advanon.interview.services

import com.advanon.interview.entities.MapEntity
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
    val log: Logger = LoggerFactory.getLogger(this.javaClass)

    fun saveOrUpdateMap(mapEntity: MapEntity): MapEntity {
        val storedMap = mapRepository.findByDataIdAndDataType(mapEntity.data.id, mapEntity.data.type)
        storedMap?.let { mapEntity.id = it.id }
        return mapRepository.save(mapEntity)
    }
}