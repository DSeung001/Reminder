package com.example.reminder.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.reminder.dto.History
import com.example.reminder.dto.Todo

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: History)

    @Update
    fun update(dto: History)

    @Delete
    fun delete(dto: History)

    @Query("SELECT * FROM historyTable WHERE todo_id = (:todoId) AND setting_on = (:settingOn)")
    fun selectOne(todoId: Long, settingOn: String): History
}