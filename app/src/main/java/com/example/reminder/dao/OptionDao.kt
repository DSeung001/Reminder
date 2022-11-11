package com.example.reminder.dao

import androidx.room.*
import com.example.reminder.dto.Option

@Dao
interface OptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dto: Option)

    @Update
    fun update(dto: Option)

    @Query("SELECT * FROM optionTable WHERE id = (:id)")
    fun selectOneById(id: Long): Option

    @Query("SELECT * FROM optionTable WHERE option_name = (:optionName)")
    fun selectOneByOptionName(optionName: String): Option
}