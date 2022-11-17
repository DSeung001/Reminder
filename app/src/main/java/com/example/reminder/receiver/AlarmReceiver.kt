package com.example.reminder.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.reminder.Constant.Companion.ALARM_CHANNEL_ID
import com.example.reminder.Constant.Companion.ALARM_NOTIFICATION_ID
import com.example.reminder.MainActivity
import com.example.reminder.R
import com.example.reminder.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class AlarmReceiver : BroadcastReceiver() {

    lateinit var notificationManager: NotificationManager

    val todoRepository = TodoRepository.get()

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Notification 을 띄우기 위한 Channel 을 등록을 위한 메소드
        createNotificationChannel()
        // Notification 등록을 위한 메소드
        deliverNotification(context)
    }

    // Notification 을 띄우기 위한 Channel 등록
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                ALARM_CHANNEL_ID, // 채널의 아이디
                "Reminder channel.", // 채널의 이름
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true) // 불빛
            notificationChannel.lightColor = Color.RED // 색상
            notificationChannel.enableVibration(true) // 진동 여부
            notificationChannel.description = "Reminder push alarm." // 채널 정보
            notificationManager.createNotificationChannel(
                notificationChannel
            )
        }
    }

    // Notification 등록
    private fun deliverNotification(context: Context) {
        Log.d("test","AlarmReceiver, deliverNotification")

        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            ALARM_NOTIFICATION_ID, // requestCode
            contentIntent, // 알림 클릭 시 이동할 인텐트
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        var content = "없습니다.";

        val builder = NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alarm) // 아이콘
            .setContentText(content) // 내용
            .setContentIntent(contentPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        CoroutineScope(Dispatchers.IO).launch {
            var todayTodoCount = 0
            val todayString = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
            val todoCount = todoRepository.getCount(todayString)

            builder.setContentTitle("오늘 할 일은")
            if(todoCount.isNotEmpty()){
                todayTodoCount = todoCount[0].count.toInt()
            }
            if (todayTodoCount > 0){
                builder.setContentText(todayTodoCount.toString()+"개 있습니다.")
            }
            notificationManager.notify(ALARM_NOTIFICATION_ID, builder.build())
        }
    }
}