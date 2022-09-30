package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
class Todo(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") val title:String, // 제목
    @ColumnInfo(name = "started_at") val started_at: String, // 시작일
    @ColumnInfo(name = "repeat") var repeat: Int, // 반복 주기
    @ColumnInfo(name = "content") val content:String, // 내용
    @ColumnInfo(name = "delay") var delay: Int, // 미룬 횟수
    @ColumnInfo(name = "created_at") val created_at: String, // 생성일, 수정일
    ): java.io.Serializable{
}