package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "historyTable",
    foreignKeys = [ForeignKey(
        entity = Todo::class,
        childColumns = ["todo_id"],
        parentColumns = ["id"]
    )]
)
class History(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "todo_id") val todo_id: Long,
    @ColumnInfo(name = "result") var result: Boolean, // 완료 여부
    @ColumnInfo(name = "setting_on") val setting_on: String, //
    ): java.io.Serializable{
}