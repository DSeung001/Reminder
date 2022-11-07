package com.example.reminder.dao

import androidx.room.Dao
import androidx.room.Update
import com.example.reminder.dto.Setting

@Dao
interface SettingDao {
    @Update
    fun update(dto: Setting)
}