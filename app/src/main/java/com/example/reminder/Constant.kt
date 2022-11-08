package com.example.reminder

class Constant {
    companion object {
        // DATABASE NAME
        const val DATABASE_NAME = "todo-database.db"

        // 아이디 선언
        const val ALARM_NOTIFICATION_ID = 0
        const val ALARM_CHANNEL_ID = "reminder_push_alarm"

        const val DELAY_NOTIFICATION_ID = 1
        const val DELAY_CHANNEL_ID = "reminder_auto_delay"

        // 알림 시간 설정
        const val ALARM_TIMER = 5
    }
}