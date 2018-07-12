package com.advanon.interview.entities

data class Tile(val x: Int, val y: Int, val type: String) {

    /** moves this coordinate by another coordinate  */
    fun move(vector: Tile): Tile {
        return Tile(x + vector.x, y + vector.y, "land")
    }
}