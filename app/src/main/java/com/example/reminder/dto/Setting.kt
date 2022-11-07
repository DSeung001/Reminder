package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settingTable")
class Setting(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "alarm_time") var alarm_time: String = "11:00",
    @ColumnInfo(name = "auto_delay") var auto_delay: Int = 0 // 제목
    ): java.io.Serializable{
}