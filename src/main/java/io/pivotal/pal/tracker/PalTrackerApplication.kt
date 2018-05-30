package io.pivotal.pal.tracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class PalTrackerApplication

fun main(args: Array<String>) {
    SpringApplication.run(PalTrackerApplication::class.java, *args)
}
