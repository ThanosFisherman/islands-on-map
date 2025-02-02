package io.github.thanosfisherman.islands.controllers

import io.github.thanosfisherman.islands.components.RestClient
import io.github.thanosfisherman.islands.entities.*
import io.github.thanosfisherman.islands.services.IIslandService
import io.github.thanosfisherman.islands.services.IMapService
import io.github.thanosfisherman.islands.services.IslandRepository
import io.github.thanosfisherman.islands.services.MapRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ExtendWith(SpringExtension::class)
@WebMvcTest(controllers = [IslandController::class])
class IslandControllerTest {

    @Autowired
    lateinit var mockmvc: MockMvc

    @MockBean
    lateinit var restClient: RestClient

    @MockBean
    lateinit var islandService: IIslandService

    @MockBean
    lateinit var islandRepository: IslandRepository

    @MockBean
    lateinit var mapRepository: MapRepository

    @MockBean
    lateinit var mapService: IMapService

    private val tileList = listOf(
            Tile(0, 0, TileType.LAND),
            Tile(0, 1, TileType.LAND),
            Tile(1, 0, TileType.LAND),
            Tile(1, 1, TileType.LAND))
    private val mapEntity: MapEntity = MapEntity("1", Data("3", "map", Link("")), Attribute(tileList))
    private var islandEntity: IslandEntity = IslandEntity(tileList, "1")

    @Before
    fun setUp() {
        BDDMockito.given(islandRepository.findAll()).willReturn(listOf(islandEntity))
        BDDMockito.given(mapRepository.findAll()).willReturn(listOf(mapEntity))
    }

    @Test
    fun populateMapsAndIslandsShouldReturnOk() {
        //given
        BDDMockito.given(restClient.getMap()).willReturn(mapEntity)
        BDDMockito.given(mapService.saveOrUpdateMap(mapEntity)).willReturn(mapEntity)

        //when
        val result = mockmvc.perform(MockMvcRequestBuilders.post("/api/maps"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
        BDDMockito.verify(islandService).locateAndSaveIslands(mapEntity)

    }

    @Test
    fun getAllIslands() {
        BDDMockito.given(islandService.getAllIslands()).willReturn(mutableListOf(islandEntity))
        mockmvc.perform(MockMvcRequestBuilders.get("/api/islands"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
        BDDMockito.verify(islandService).getAllIslands()
    }

    @Test
    fun getMaps() {
        BDDMockito.given(mapService.getMaps()).willReturn(listOf(mapEntity))

        mockmvc.perform(MockMvcRequestBuilders.get("/api/maps"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
        BDDMockito.verify(mapService).getMaps()
    }

    @Test
    fun printMap() {
        val expectedBuilder = StringBuilder("<pre>\n\n").append("XX\n").append("XX\n").append("\n").append("</pre>\n")
        BDDMockito.given(mapService.printMaps()).willReturn(expectedBuilder)
        val results = mockmvc.perform(MockMvcRequestBuilders.get("/api/maps/ascii"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        Assertions.assertThat(results.response.contentAsString).isEqualTo(expectedBuilder.toString())
    }
}