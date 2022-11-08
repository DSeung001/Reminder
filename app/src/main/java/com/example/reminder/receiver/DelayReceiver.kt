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

class DelayReceiver : BroadcastReceiver() {

    private val settingRepository = SettingRepository.get()
    private val todoRepository = TodoRepository.get()

    override fun onReceive(context: Context, intent: Intent) {
        notFinishDelay()
    }

    fun notFinishDelay (){
        CoroutineScope(Dispatchers.IO).launch {
            val setting = settingRepository.getSetting(1)

            if(setting != null && setting.auto_delay == 1){
                Log.e("check", "00:01 실행")
            }
        }
    }
}