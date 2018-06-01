package io.pivotal.pal.tracker

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Date
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement.RETURN_GENERATED_KEYS
import javax.sql.DataSource


class JdbcTimeEntryRepository(dataSource: DataSource) : TimeEntryRepository {

    private var jdbcTempate: JdbcTemplate = JdbcTemplate(dataSource)

    override fun find(id: Long): TimeEntry? {
        return jdbcTempate.query("select * from time_entries where id = ?", TimeEntryRowMapper(), id ).firstOrNull()
    }

    override fun create(timeEntry: TimeEntry): TimeEntry? {
        val generatedKeyHolder = GeneratedKeyHolder()

        jdbcTempate.update({ connection ->
            val statement = connection.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) " + "VALUES (?, ?, ?, ?)",
                    RETURN_GENERATED_KEYS
            )

            statement.setLong(1, timeEntry.projectId)
            statement.setLong(2, timeEntry.userId)
            statement.setDate(3, Date.valueOf(timeEntry.date))
            statement.setInt(4, timeEntry.hours)

            statement
        }, generatedKeyHolder)

        return find(generatedKeyHolder.key?.toLong()!!)

    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        jdbcTempate.update("update time_entries set project_id = ?,user_id = ?,date= ?,hours= ? where id =?",
                timeEntry.projectId, timeEntry.userId, timeEntry.date, timeEntry.hours, id)
        return find(id)

    }

    override fun delete(id: Long): TimeEntry? {
        val timeEntry = find(id.toLong())
        jdbcTempate.update("delete from time_entries where id =? ",
                id)
        return timeEntry
    }

    override fun list(): List<TimeEntry> {
         return jdbcTempate.query("select * from time_entries", TimeEntryRowMapper())
    }

    class TimeEntryRowMapper: RowMapper<TimeEntry> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): TimeEntry? {
            return TimeEntry(rs.getLong("id"),
                    rs.getLong("project_id"),
                    rs.getLong("user_id"),
                    rs.getDate("date").toLocalDate(),
                    rs.getInt("hours"))

        }
    }

}