package com.example.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reminder.databinding.ActivityCalendarBinding


class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}