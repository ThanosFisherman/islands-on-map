package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.algorithms.IslandDetector
import io.github.thanosfisherman.islands.entities.*
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class IslandServiceTest {

    @InjectMocks
    lateinit var islandService: IslandService
    @Mock
    lateinit var islandDetector: IslandDetector
    @Mock
    lateinit var islandRepository: IslandRepository
    private val tileList = listOf(
            Tile(0, 0, TileType.LAND),
            Tile(0, 1, TileType.LAND),
            Tile(1, 0, TileType.LAND),
            Tile(1, 1, TileType.LAND))
    private val mapEntity: MapEntity = MapEntity("1", Data("3", "map", Link("")), Attribute(tileList))

    @Before
    fun setUp() {
        //given
        BDDMockito.given(islandDetector.detectIslands("1", tileList)).willReturn(listOf(IslandEntity(tileList, "1")))
        BDDMockito.given(islandRepository.deleteAllByMapId("1")).willReturn(listOf(IslandEntity(tileList, "1")))
        BDDMockito.given(islandRepository.findAll()).willReturn(listOf(IslandEntity(tileList, "1")))
    }

    @Test
    fun locateAndSaveIslands() {
        //when
        islandService.locateAndSaveIslands(mapEntity)

        //then
        BDDMockito.verify(islandRepository, BDDMockito.times(1)).deleteAllByMapId("1")
        BDDMockito.verify(islandRepository, BDDMockito.times(1)).save(BDDMockito.any())

    }

    @Test
    fun deleteAllByMapId() {
        //when
        val islands = islandService.deleteAllByMapId(mapEntity)

        //then
        Assertions.assertThat(islands).size().isEqualTo(1)
        Assertions.assertThat(islands[0].mapId).isEqualTo("1")
    }

    @Test
    fun getAllIslands() {
        val islands = islandService.getAllIslands()
        Assertions.assertThat(islands).size().isEqualTo(1)
        BDDMockito.verify(islandRepository, BDDMockito.times(1)).findAll()
    }

    @Test
    fun getIslandById() {
        //Meh extension functions don't seem to play nicely with Mockito

        //given
        //BDDMockito.given(islandRepository.findOneById("islandId1")).willReturn(IslandEntity(tileList, "1", "islandId1"))
        //when
        //val island = islandService.getIslandById("islandId1")
        //then
        //Assertions.assertThat(island?.id).isEqualTo("islandId1")
    }
}