package com.example.reminder.dao

import androidx.room.*
import com.example.reminder.dto.Setting
import com.example.reminder.dto.Todo

@Dao
interface SettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Setting)

    @Update
    fun update(dto: Setting)

    @Query("SELECT * FROM settingTable WHERE id = (:id)")
    fun selectOne(id: Long): Setting
}