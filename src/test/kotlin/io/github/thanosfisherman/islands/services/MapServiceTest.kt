package io.github.thanosfisherman.islands.services

import io.github.thanosfisherman.islands.entities.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
class MapServiceTest {

    @InjectMocks
    lateinit var mapService: MapService

    @Mock
    lateinit var mapRepository: MapRepository
    private val tileList = listOf(
        Tile(0, 0, TileType.LAND),
        Tile(0, 1, TileType.LAND),
        Tile(1, 0, TileType.LAND),
        Tile(1, 1, TileType.LAND)
    )
    private val mapEntity: MapEntity = MapEntity("1", Data("3", "map", Link("")), Attribute(tileList))

    @BeforeEach
    fun setUp() {
        //given
        BDDMockito.given(mapRepository.findByDataIdAndDataType("3", "map")).willReturn(mapEntity)
        BDDMockito.given(mapRepository.save(mapEntity)).willReturn(mapEntity)
        BDDMockito.given(mapRepository.findAll()).willReturn(listOf(mapEntity))
    }

    @Test
    fun saveOrUpdateMap() {
        //when
        val map = mapService.saveOrUpdateMap(mapEntity)

        //then
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).findByDataIdAndDataType("3", "map")
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).save(map)
        BDDMockito.verifyNoMoreInteractions(mapRepository)
    }

    @Test
    fun getMaps() {
        //when
        mapService.getMaps()

        //then
        BDDMockito.verify(mapRepository, BDDMockito.times(1)).findAll()
        BDDMockito.verifyNoMoreInteractions(mapRepository)
    }

    @Test
    fun printMaps() {

        //when
        val actualBuilder = mapService.printMaps()
        println(actualBuilder.toString())
        //then
        val expectedBuilder = StringBuilder("<pre>\n\n").append("XX\n").append("XX\n").append("\n").append("</pre>\n")
        Assertions.assertThat(actualBuilder.toString()).isEqualTo(expectedBuilder.toString())
    }

    @Test
    fun findMapById() {

        //given
        BDDMockito.given(mapRepository.findById("1")).willReturn(Optional.of(mapEntity))
        //when
        val map = mapService.findMapById("1")
        //then
        Assertions.assertThat(map?.id).isEqualTo("1")
    }
}