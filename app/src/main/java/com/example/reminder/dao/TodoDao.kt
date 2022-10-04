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

    @Query("select * from todoTable where Cast((JulianDay('2022-10-05') - JulianDay( date('now'))) As Integer) % repeat = 0")
    fun list(): LiveData<MutableList<Todo>>

    @Query("select * from todoTable where id = (:id)")
    fun selectOne(id: Long): Todo

    @Update
    fun update(dto: Todo)

    @Delete
    fun delete(dto: Todo)
}