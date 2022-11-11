package com.example.reminder.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todoTable")
class Todo(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "title") var title:String, // 제목
    @ColumnInfo(name = "started_at") var started_at: String, // 시작일
    @ColumnInfo(name = "repeat") var repeat: Int, // 반복 주기
    @ColumnInfo(name = "content") var content:String, // 내용
    @ColumnInfo(name = "delay") var delay: Int, // 미룬 횟수
    @ColumnInfo(name = "created_at") var created_at: String, // 생성일, 수정일
    @ColumnInfo(name = "expired_at") var expired_at: String?, // 만료일, 미루기 기능시 현재 날짜로 만료 됨
    ): java.io.Serializable{
}