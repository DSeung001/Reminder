package com.example.reminder.setting

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminder.Constant
import com.example.reminder.receiver.DelayReceiver
import java.util.*

class DelaySetting {

    fun setting(context: Context, alarmManager: AlarmManager) {

        Log.d("test", "DelaySetting : setting")

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            Constant.DELAY_NOTIFICATION_ID,
            Intent(context, DelayReceiver::class.java),
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 1)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }
}