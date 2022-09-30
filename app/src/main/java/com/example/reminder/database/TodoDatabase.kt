package com.example.reminder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminder.dao.TodoDao
import com.example.reminder.dto.Todo

@Database(entities = arrayOf(Todo::class), version = 2)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}