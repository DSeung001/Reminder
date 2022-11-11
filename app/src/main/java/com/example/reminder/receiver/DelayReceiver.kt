package com.example.reminder.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminder.repository.SettingRepository
import com.example.reminder.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DelayReceiver : BroadcastReceiver() {

    private val settingRepository = SettingRepository.get()
    private val todoRepository = TodoRepository.get()

    override fun onReceive(context: Context, intent: Intent) {
        notFinishDelay()
    }

    private fun notFinishDelay (){
        CoroutineScope(Dispatchers.IO).launch {
            val setting = settingRepository.getSetting(1)

            if(setting != null && setting.auto_delay == 1){
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                todoRepository.autoDelayUpdate(SimpleDateFormat("yyyy-MM-dd").format(calendar.time))
            }
        }
    }
}