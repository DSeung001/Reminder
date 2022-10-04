package com.example.reminder.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.reminder.dto.History

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: History)

    @Query("select * from historyTable where id = (:todo_id) and setting_on = (:setting_on)")
    fun selectByTodo(todo_id: Long, setting_on: String): History

    @Update
    fun update(dto: History)

    @Delete
    fun delete(dto: History)
}