package com.example.reminder.repository

import android.content.Context
import androidx.room.Room
import com.example.reminder.Constant
import com.example.reminder.database.TodoDatabase
import com.example.reminder.dto.Setting

class SettingRepository private constructor(context: Context){

    private val database: TodoDatabase = Room.databaseBuilder(
        context.applicationContext,
        TodoDatabase::class.java,
        Constant.DATABASE_NAME
    ).build()

    private val settingDao = database.settingDao()

    fun insert(dto: Setting) = settingDao.insert(dto)

    suspend fun update(dto: Setting) = settingDao.update(dto)

    fun getSetting(id: Long): Setting = settingDao.selectOne(id)

    companion object {
        private var INSTANCE: SettingRepository?=null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = SettingRepository(context)
            }
        }

        fun get(): SettingRepository {
            return INSTANCE ?:
            throw IllegalStateException("SettingRepository must be initialized")
        }
    }
}