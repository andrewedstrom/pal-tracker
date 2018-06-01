package io.pivotal.pal.tracker

import java.sql.Time
import java.util.concurrent.atomic.AtomicLong

class InMemoryTimeEntryRepository : TimeEntryRepository {
    private val timeEntries = mutableMapOf<Long, TimeEntry>()
    private val idCounter: AtomicLong = AtomicLong(0)

    override fun find(id: Long): TimeEntry? {
        return timeEntries[id]
    }

    override fun create(timeEntry: TimeEntry): TimeEntry? {
        timeEntry.id = idCounter.incrementAndGet()
        timeEntries[timeEntry.id] = timeEntry
        return timeEntry
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        return find(id)?.let {
            timeEntries[it.id] = TimeEntry(id, timeEntry.projectId, timeEntry.userId, timeEntry.date, timeEntry.hours)
            timeEntries[it.id]
        }
    }

    override fun list(): List<TimeEntry> {
        return timeEntries.values.toList()
    }

    override fun delete(id: Long): TimeEntry? {
        return timeEntries.remove(id)
    }
}