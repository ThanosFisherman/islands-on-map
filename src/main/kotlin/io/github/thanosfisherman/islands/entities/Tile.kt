package io.github.thanosfisherman.islands.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName

data class Tile(val x: Int, val y: Int, var type: TileType) {
    val isLand: Boolean @JsonIgnore get() = type == TileType.LAND
    val coords: Coords @JsonIgnore get() = Coords(x, y)

    constructor(coords: Coords, type: TileType) : this(coords.x, coords.y, type)
}

data class Coords(val x: Int, val y: Int)

@JsonRootName("type")
enum class TileType {
    @JsonProperty("water")
    WATER,
    @JsonProperty("land")
    LAND
}