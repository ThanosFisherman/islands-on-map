package io.github.thanosfisherman.islands.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "maps")
@JsonIgnoreProperties(ignoreUnknown = true)
data class MapEntity(@Id var id: String?,
                     val data: Data,
                     val attributes: Attribute

)

data class Data(val id: String, val type: String, val links: Link)
data class Link(val self: String)
data class Attribute(val tiles: List<Tile>)