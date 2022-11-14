package com.example.reminder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.CalendarAdapter
import com.example.reminder.databinding.ActivityCalendarBinding
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.repository.TodoRepository
import com.example.reminder.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timeInMillis = intent.getLongExtra("timeInMillis", 0)
        val currentCalendar:Calendar = Calendar.getInstance()
        var currentTimeString = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)

        Log.d("test", currentTimeString)

        if (timeInMillis > 0){
            currentCalendar.setTimeInMillis(timeInMillis)
            currentTimeString = SimpleDateFormat("yyyy-MM-dd").format(currentCalendar.time)
        }
        binding.calendarView.setDateSelected(currentCalendar, true)

        todoViewModel = ViewModelProvider(this, ViewModelFactory(currentTimeString)).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            calendarAdapter.update(it)
        }

        calendarAdapter = CalendarAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = calendarAdapter


        binding.calendarView.setOnDateChangedListener{ widget, date, selected ->
            val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date.calendar.time)

            Log.d("test", selectedDate)

            todoViewModel.reInit(selectedDate)
            todoViewModel.todoList.observe(this){
                calendarAdapter.update(it)
            }
        }
    }
}