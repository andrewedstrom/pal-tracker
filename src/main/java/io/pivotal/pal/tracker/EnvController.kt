package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EnvController(
        @Value("\${PORT:NOT SET}") var port: String,
        @Value("\${MEMORY_LIMIT:NOT SET}") var memLimit: String,
        @Value("\${CF_INSTANCE_INDEX:NOT SET}") var instanceIndex: String,
        @Value("\${CF_INSTANCE_ADDR:NOT SET}") var instanceAddr: String
) {

    @GetMapping("/env")
    fun getEnv(): Map<String, String> {
        return mapOf(
                "PORT" to port,
                "MEMORY_LIMIT" to memLimit,
                "CF_INSTANCE_INDEX" to instanceIndex,
                "CF_INSTANCE_ADDR" to instanceAddr
        )
    }
}
