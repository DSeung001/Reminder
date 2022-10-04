package com.example.reminder.repository
import android.content.Context
import androidx.room.Room
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.History

private const val DATABASE_NAME = "todo-database.db"

class HistoryRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val historyDao = database.historyDao()

    fun getHistoryByTodo(todo_id: Long, setting_on:String): History = historyDao.selectByTodo(todo_id,setting_on)

    fun insert(dto: History) = historyDao.insert(dto)

    suspend fun update(dto: History) = historyDao.update(dto)

    fun delete(dto: History) = historyDao.delete(dto)

    companion object {
        private var INSTANCE: HistoryRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = HistoryRepository(context)
            }
        }

        fun get(): HistoryRepository {
            return INSTANCE ?:
            throw IllegalStateException("HistoryRepository must be initialized")
        }
    }
}