package com.example.reminder.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.reminder.dto.Todo

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Todo)

    @Query("" +
            "SELECT td.id as id, title, started_at, repeat, content, delay, result, created_at, JulianDay( date('now')) AS num_now, JulianDay(started_at) AS num_started_at " +
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON todo_id = td.id " +
            "AND setting_on = date('now')" +
            "WHERE num_now >= num_started_at " +
            "AND Cast((num_now - num_started_at) As Integer) % repeat = 0 " +
            "AND expired_at IS NULL")
    fun list(): LiveData<MutableList<TodoHistory>>

    data class TodoHistory(
        val id: Long,
        val title: String,
        val started_at: String,
        val repeat: Int,
        val content: String?,
        val delay: Int,
        val result: Boolean?,
        val created_at: String,
        val expired_at: String?,
        val num_now: Int,
        val num_started_at:Int
    )

    data class TodoCount(
        val count:Long
    )

    @Query("" +
            "SELECT td.id, title, started_at, repeat, content, delay, result, created_at, JulianDay((:selectOnDate)) AS num_now, JulianDay(started_at) AS num_started_at " +
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON todo_id = td.id " +
            "AND setting_on = (:selectOnDate) " +
            "WHERE num_now >= num_started_at " +
            "AND Cast((num_now - num_started_at) As Integer) % repeat = 0 " +
            "AND (expired_at IS NULL OR JulianDay(expired_at) > JulianDay((:selectOnDate)) )")
    fun listOnDate(selectOnDate:String):LiveData<MutableList<TodoHistory>>

    @Query("SELECT * FROM todoTable WHERE id = (:id)")
    fun selectOne(id: Long): Todo

    @Update
    fun update(dto: Todo)

    @Delete
    fun delete(dto: Todo)

    @Query("" +
            "SELECT COUNT(*) AS count " +
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON todo_id = td.id " +
            "AND setting_on = date('now')" +
            "WHERE JulianDay(date('now')) >= JulianDay(started_at) " +
            "AND Cast((JulianDay(date('now')) - JulianDay(started_at)) As Integer) % repeat = 0 " +
            "AND expired_at IS NULL")
    fun getCount(): List<TodoCount>

    @Query("" +
            "UPDATE todoTable SET delay = delay+1 WHERE ID in (" +
            "SELECT td.id as id " +
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON todo_id = td.id " +
            "AND setting_on = (:selectOnDate) "+
            "WHERE JulianDay(date('now')) >= JulianDay(started_at) " +
            "AND Cast((JulianDay(date('now')) - JulianDay(started_at)) As Integer) % repeat = 0 " +
            "AND (expired_at IS NULL OR JulianDay(expired_at) > JulianDay((:selectOnDate)) ))")
    fun autoDelayUpdate(selectOnDate:String)
}