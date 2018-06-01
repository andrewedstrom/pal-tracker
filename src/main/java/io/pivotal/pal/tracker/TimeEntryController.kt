package io.pivotal.pal.tracker

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TimeEntryController(val timeEntryRepository: TimeEntryRepository) {
    @PostMapping("/time-entries")
    fun create(@RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        return ResponseEntity(timeEntryRepository.create(timeEntry), HttpStatus.CREATED)
    }

    @GetMapping("/time-entries/{id}")
    fun read(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        return timeEntryRepository.find(id)?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/time-entries")
    fun list(): ResponseEntity<List<TimeEntry>> {
        return ResponseEntity(timeEntryRepository.list(), HttpStatus.OK)
    }

    @PutMapping("/time-entries/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody timeEntry: TimeEntry): ResponseEntity<TimeEntry> {
        return timeEntryRepository.update(id, timeEntry)?.let {
            ResponseEntity(it, HttpStatus.OK)
        } ?: ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @DeleteMapping("/time-entries/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<TimeEntry> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(timeEntryRepository.delete(id))
    }
}