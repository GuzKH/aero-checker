package com.per.aero_checker.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.per.aero_checker.configuration.AirportConfig
import com.per.aero_checker.configuration.CacheConfiguration
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
open class PulkovoClient(
        private val airportConfig: AirportConfig,
        private val restTemplate: RestTemplate,
        private val objectMapper: ObjectMapper
) {

    @Cacheable(CacheConfiguration.CACHE_ONE)
    open fun getArrivals(): PulkovoResponse? {
        val body = restTemplate
                .getForEntity(
                        airportConfig.pulkovo.arrives,
                        String::class.java)
                .body

        objectMapper.registerModule(JavaTimeModule())
        return objectMapper.readValue(body, PulkovoResponse::class.java)
    }

    @Cacheable(CacheConfiguration.CACHE_ONE)
    open fun getDepartures(): PulkovoResponse? {
        val body = restTemplate
                .getForEntity(
                        airportConfig.pulkovo.departures,
                        String::class.java)
                .body

        objectMapper.registerModule(JavaTimeModule())
        return objectMapper.readValue(body, PulkovoResponse::class.java)
    }

}