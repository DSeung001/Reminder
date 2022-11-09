package com.example.reminder.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.reminder.Constant
import com.example.reminder.receiver.AlarmReceiver
import com.example.reminder.repository.SettingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AlarmSetting {
    val settingRepository = SettingRepository.get()

    fun setting(context: Context, alarmManager: AlarmManager){
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Constant.ALARM_NOTIFICATION_ID,
            Intent(context, AlarmReceiver::class.java),
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        CoroutineScope(Dispatchers.IO).launch {
            var hour = 11
            var minute = 0
            val setting = settingRepository.getSetting(1)
            if (setting != null && setting.alarm_time != "시간") {
                hour = setting.alarm_time.substring(0, 2).toInt()
                minute = setting.alarm_time.substring(3, 5).toInt()
            }

            val calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
            }
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }
}