package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "optionTable")
class Option(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "option_name") var option_name: String,
    @ColumnInfo(name = "option_value") var option_value: String
    ): java.io.Serializable{
}


/*
*
* name, value로 값을 바꾸고
*  alarm_time = "11:00"
*  auto_delay = "false"
*  first_alarm_setting = "false"
*  first_alarm_setting = "false"
* */
