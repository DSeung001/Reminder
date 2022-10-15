package com.example.reminder.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.reminder.dao.TodoDao
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.Todo

private const val DATABASE_NAME = "todo-database.db"

class TodoRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val todoDao = database.todoDao()

    fun list(): LiveData<MutableList<TodoDao.TodoHistory>> = todoDao.list()

    fun listOnDate(selectOnDate:String): LiveData<MutableList<TodoDao.TodoHistory>> = todoDao.listOnDate(selectOnDate)

    fun getTodo(id: Long): Todo = todoDao.selectOne(id)

    fun insert(dto: Todo) = todoDao.insert(dto)

    suspend fun update(dto: Todo) = todoDao.update(dto)

    fun delete(dto: Todo) = todoDao.delete(dto)

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