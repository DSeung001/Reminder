package com.example.reminder.repository
import android.content.Context
import androidx.room.Room
import com.example.reminder.Constant
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.History

class HistoryRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    private val historyDao = database.historyDao()

    fun insert(dto: History) = historyDao.insert(dto)

    suspend fun update(dto: History) = historyDao.update(dto)

    fun delete(dto: History) = historyDao.delete(dto)

    fun getHistory(todoId: Long, settingOn: String):History = historyDao.selectOne(todoId, settingOn)

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