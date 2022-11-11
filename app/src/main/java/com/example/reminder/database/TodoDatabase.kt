package com.example.reminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminder.dao.TodoDao
import com.example.reminder.dto.Todo
import com.example.reminder.dao.HistoryDao
import com.example.reminder.dao.OptionDao
import com.example.reminder.dto.History
import com.example.reminder.dto.Option

@Database(
    entities = [Todo::class, History::class, Option::class],
    version = 1,
)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun historyDao(): HistoryDao
    abstract fun optionDao(): OptionDao
}

