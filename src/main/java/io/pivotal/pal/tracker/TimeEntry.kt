package io.pivotal.pal.tracker

import java.time.LocalDate

data class TimeEntry(val projectId: Long,
                     val userId: Long,
                     val date: LocalDate,
                     val hours: Int) {
    var id: Long = 0L

    constructor(id: Long, projectId: Long, userId: Long, date: LocalDate, hours: Int) :this( projectId, userId, date, hours) {
        this.id = id
    }

    constructor() : this(-1L, -1L, LocalDate.MIN, -1)
}