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
            "SELECT id, title, started_at, repeat, content, delay, created_at, JulianDay( date('now')) AS num_now, JulianDay(started_at) AS num_started_at " +
            "FROM todoTable " +
            "WHERE num_now >= num_started_at " +
            "AND Cast((num_now - num_started_at) As Integer) % repeat = 0 " +
            "AND (num_now == num_started_at AND repeat != 1) != 1 ")
    fun list(): LiveData<MutableList<Todo>>


    // left join 해서 result 값 가져오면 클리어 여부 쉽게 알 수 있음
    //
    @Query("" +
            "SELECT id, title, started_at, repeat, content, delay, created_at, JulianDay((:selectOnDate)) AS num_now, JulianDay(started_at) AS num_started_at " +
            "FROM todoTable " +
            "WHERE num_now >= num_started_at " +
            "AND Cast((num_now - num_started_at) As Integer) % repeat = 0 " +
            "AND (num_now == num_started_at AND repeat != 1) != 1 ")
    fun listOnDate(selectOnDate:String):LiveData<MutableList<Todo>>

    @Query("SELECT * FROM todoTable WHERE id = (:id)")
    fun selectOne(id: Long): Todo

    @Update
    fun update(dto: Todo)

    @Delete
    fun delete(dto: Todo)
}