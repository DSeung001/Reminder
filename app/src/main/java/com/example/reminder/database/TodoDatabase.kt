package com.example.reminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminder.dao.TodoDao
import com.example.reminder.dto.Todo
import com.example.reminder.dao.HistoryDao
import com.example.reminder.dto.History

@Database(entities = arrayOf(Todo::class, History::class), version = 2)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun historyDao(): HistoryDao
}