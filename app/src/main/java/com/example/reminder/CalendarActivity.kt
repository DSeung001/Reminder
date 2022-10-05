package com.example.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reminder.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { view, year, month, day ->
            // 기존 메인에서 보여주던 쿼리문에서 date만 바꾸는 형식으로 해서 뿌려주면 될듯
            // 1. 기존 쿼리에서 파라미터 받는 형식 , 2. 신규 쿼리 작성
            Toast.makeText(this, String.format("%d /%d / %d", year, month+1, day), Toast.LENGTH_SHORT).show()
        }

    }
}