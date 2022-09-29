package com.example.reminder.config

import android.app.Application
import com.example.reminder.repository.TodoRepository

class ApplicationClass:Application() {

    override fun onCreate() {
        super.onCreate()

        TodoRepository.initialize(this)
    }

}