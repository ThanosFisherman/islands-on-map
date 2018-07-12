package com.advanon.interview;

import com.advanon.interview.entities.MapEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepo extends MongoRepository<MapEntity, String> {
    MapEntity findByDataIdAndDataType(String dataId, String dataType);
}
