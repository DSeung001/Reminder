package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
class Todo (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") val title:String, // 제목
    @ColumnInfo(name = "content") val content:String, // 내용
    @ColumnInfo(name = "timestamp") val timestamp: String, // 생성/수정일
    @ColumnInfo(name = "isChecked") var isChecked: Boolean // 완료 여부
    ): java.io.Serializable{
}