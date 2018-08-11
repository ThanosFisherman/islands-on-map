package io.github.thanosfisherman.islands.entities

data class Tile(val x: Int, val y: Int, var type: String) {
    val isLand: Boolean get() = type == "land"
}