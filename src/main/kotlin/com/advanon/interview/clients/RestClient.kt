package com.advanon.interview.clients

import com.advanon.interview.entities.MapEntity
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component

@Component
class RestClient(private val restTemplateBuilder: RestTemplateBuilder) {

    private val url = "https://private-2e8649-advapi.apiary-mock.com/map"

    fun getMap(): MapEntity {
        val restTemplate = restTemplateBuilder.build()
        val mapEntity = restTemplate.getForObject(url, MapEntity::class.java)
        return mapEntity ?: throw RuntimeException("Couldn't get maps from url. Try again later")
    }
}