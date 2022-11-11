package com.example.reminder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.reminder.Constant
import com.example.reminder.dao.TodoDao
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.Todo

class TodoRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    private val todoDao = database.todoDao()

    fun listOnDate(selectOnDate:String): LiveData<MutableList<TodoDao.TodoHistory>> = todoDao.listOnDate(selectOnDate)

    fun getTodo(id: Long): Todo = todoDao.selectOne(id)

    fun insert(dto: Todo) = todoDao.insert(dto)

    suspend fun update(dto: Todo) = todoDao.update(dto)

    fun delete(dto: Todo) = todoDao.delete(dto)

    fun getCount(selectOnDate:String) = todoDao.getCount(selectOnDate)

    fun toDelayList(selectOnDate:String) = todoDao.toDelayList(selectOnDate)

    companion object {
        private var INSTANCE: TodoRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TodoRepository(context)
            }
        }

        fun get(): TodoRepository {
            return INSTANCE ?:
            throw IllegalStateException("TodoRepository must be initialized")
        }
    }
}