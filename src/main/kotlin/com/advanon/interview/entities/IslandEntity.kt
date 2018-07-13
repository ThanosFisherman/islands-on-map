package com.advanon.interview.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "islands")
data class IslandEntity(
        val tiles: List<Tile>,
        val mapId: String,
        @Id @JsonIgnore var id: String? = null)