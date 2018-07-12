package com.advanon.interview.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "islands")
class IslandEntity(@Id @JsonIgnore var id: String?,
                   val tiles: List<Tile>,
                   val map_id: String)