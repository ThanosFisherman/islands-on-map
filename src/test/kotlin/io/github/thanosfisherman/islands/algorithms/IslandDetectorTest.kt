package io.github.thanosfisherman.islands.algorithms

import io.github.thanosfisherman.islands.entities.Coords
import io.github.thanosfisherman.islands.entities.Tile
import io.github.thanosfisherman.islands.entities.TileType
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class IslandDetectorTest {

    lateinit var islandDetector: IIslandDetector

    @Before
    fun setUp() {
        islandDetector = IslandDetector()
    }

    @Test
    fun detectIslandsInExample() {
        val tileList = listOf(
                Tile(1, 1, TileType.LAND),
                Tile(2, 1, TileType.LAND),
                Tile(3, 1, TileType.WATER),
                Tile(4, 1, TileType.WATER),
                Tile(5, 1, TileType.LAND),
                Tile(6, 1, TileType.WATER),
                Tile(1, 2, TileType.WATER),
                Tile(2, 2, TileType.LAND),
                Tile(3, 2, TileType.WATER),
                Tile(4, 2, TileType.WATER),
                Tile(5, 2, TileType.WATER),
                Tile(6, 2, TileType.WATER),
                Tile(1, 3, TileType.WATER),
                Tile(2, 3, TileType.WATER),
                Tile(3, 3, TileType.WATER),
                Tile(4, 3, TileType.WATER),
                Tile(5, 3, TileType.LAND),
                Tile(6, 3, TileType.WATER),
                Tile(1, 4, TileType.WATER),
                Tile(2, 4, TileType.WATER),
                Tile(3, 4, TileType.LAND),
                Tile(4, 4, TileType.LAND),
                Tile(5, 4, TileType.LAND),
                Tile(6, 4, TileType.WATER),
                Tile(1, 5, TileType.WATER),
                Tile(2, 5, TileType.WATER),
                Tile(3, 5, TileType.WATER),
                Tile(4, 5, TileType.LAND),
                Tile(5, 5, TileType.WATER),
                Tile(6, 5, TileType.WATER)
        )
        val islands = islandDetector.detectIslands("1", tileList)

        Assertions.assertThat(islands.map { it.mapId }.toSet()).isEqualTo(setOf("1", "1", "1"))
        Assertions.assertThat(islands).hasSize(3)
        Assertions.assertThat(islands.map { it.tiles.size }.toSet()).isEqualTo(setOf(3, 1, 5))

        var coordsSet = islands.filter { it.tiles.size == 1 }.flatMap { it.tiles }.map { it.coords }.toSet()

        Assertions.assertThat(coordsSet).isEqualTo(setOf(Coords(5, 1)))
        coordsSet = islands.filter { it.tiles.size == 3 }.flatMap { it.tiles }.map { it.coords }.toSet()

        Assertions.assertThat(coordsSet).isEqualTo(setOf(Coords(1, 1), Coords(2, 1), Coords(2, 2)))
        coordsSet = islands.filter { it.tiles.size == 5 }.flatMap { it.tiles }.map { it.coords }.toSet()

        Assertions.assertThat(coordsSet).isEqualTo(setOf(Coords(5, 3), Coords(3, 4), Coords(4, 4), Coords(5, 4), Coords(4, 5)))
    }

    @Test
    fun waterOnlyMapShouldHaveNoIsland() {
        val tileList = listOf(
                Tile(0, 0, TileType.WATER),
                Tile(0, 1, TileType.WATER),
                Tile(1, 0, TileType.WATER),
                Tile(1, 1, TileType.WATER)
        )
        val islands = islandDetector.detectIslands("2", tileList)

        Assertions.assertThat(islands).isEmpty()
    }

    @Test
    fun landOnlyMapShouldHaveOneIsland() {
        val tileList = listOf(
                Tile(0, 0, TileType.LAND),
                Tile(0, 1, TileType.LAND),
                Tile(1, 0, TileType.LAND),
                Tile(1, 1, TileType.LAND)
        )
        val islands = islandDetector.detectIslands("2", tileList)

        Assertions.assertThat(islands).size().isEqualTo(1)
        println(islands[0].tiles.size)
        Assertions.assertThat(islands[0].tiles).size().isEqualTo(4)
    }


    @Test
    fun diagonalTilesShouldBeSeparateIslands() {
        val tileList = listOf(
                Tile(0, 0, TileType.LAND),
                Tile(0, 1, TileType.WATER),
                Tile(1, 0, TileType.WATER),
                Tile(1, 1, TileType.LAND)
        )
        val islands = islandDetector.detectIslands("3", tileList)

        Assertions.assertThat(islands).size().isEqualTo(2)
        Assertions.assertThat(islands.all { islandEntity -> islandEntity.tiles.size == 1 }).isTrue()
    }
}