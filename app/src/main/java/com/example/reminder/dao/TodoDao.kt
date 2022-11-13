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
            "AND setting_on = (:selectOnDate)" +
            "WHERE JulianDay((:selectOnDate)) >= JulianDay(started_at) " +
            "AND Cast((JulianDay((:selectOnDate)) - JulianDay(started_at)) As Integer) % repeat = 0 " +
            "AND (expired_at IS NULL OR JulianDay(expired_at) > JulianDay((:selectOnDate)) )")
    fun getCount(selectOnDate:String): List<TodoCount>

    /*@Query("" +
            "SELECT td.id AS id, title, started_at, repeat, content, delay, created_at, expired_at " +
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON historyTable.id IS NULL " +
            "AND setting_on = (:selectOnDate)" +
            "WHERE JulianDay((:selectOnDate)) >= JulianDay(started_at) " +
            "AND Cast((JulianDay((:selectOnDate)) - JulianDay(started_at)) As Integer) % repeat = 0 " +
            "AND expired_at IS NULL ")
    fun toDelayList(selectOnDate:String): Array<Todo>*/

    @Query("" +
            "SELECT td.id AS id, title, started_at, repeat, content, delay, result, created_at, expired_at "+
            "FROM todoTable AS td " +
            "LEFT JOIN historyTable ON todo_id = td.id " +
            "AND setting_on = (:selectOnDate) "+
            "WHERE JulianDay((:selectOnDate)) >= JulianDay(started_at) " +
            "AND Cast((JulianDay((:selectOnDate)) - JulianDay(started_at)) As Integer) % repeat = 0 " +
            "AND (expired_at IS NULL OR JulianDay(expired_at) > JulianDay((:selectOnDate)) ) " +
            "AND historyTable.id IS NULL" )
    fun toDelayList(selectOnDate:String): Array<Todo>
}