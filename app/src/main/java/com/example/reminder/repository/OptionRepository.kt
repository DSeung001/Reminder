package com.example.reminder.repository

import android.content.Context
import androidx.room.Room
import com.example.reminder.Constant
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.Option

class OptionRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    private val optionDao = database.optionDao()

    fun insert(dto: Option) = optionDao.insert(dto)

    suspend fun update(dto: Option) = optionDao.update(dto)

    fun getSettingById(id: Long): Option = optionDao.selectOneById(id)

    fun getSettingByOptionName(optionName: String) : Option = optionDao.selectOneByOptionName(optionName)

    companion object {
        private var INSTANCE: OptionRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = OptionRepository(context)
            }
        }

        fun get(): OptionRepository {
            return INSTANCE ?:
            throw IllegalStateException("SettingRepository must be initialized")
        }
    }
}