package io.github.thanosfisherman.islands.entities

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

data class Tile(val x: Int, val y: Int, var type: TileType) {
    val isLand: Boolean get() = type == TileType.LAND
}

@JsonRootName("type")
enum class TileType {
    @JsonProperty("water")
    WATER,
    @JsonProperty("land")
    LAND
}