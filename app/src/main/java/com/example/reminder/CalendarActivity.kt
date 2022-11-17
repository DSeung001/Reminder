package com.example.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.CalendarAdapter
import com.example.reminder.databinding.ActivityCalendarBinding
import com.example.reminder.decorator.*
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.viewmodel.TodoViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CalendarActivity Init
        calendarInitialization()

        binding.calendarView.setOnDateChangedListener{ widget, date, selected ->
            val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date.calendar.time)
            todoViewModel.reInit(selectedDate)
            todoViewModel.todoList.observe(this){
                calendarAdapter.update(it)
            }
        }
    }

    fun calendarInitialization(){
        val currentCalendar:Calendar = Calendar.getInstance()
        val dateArray = intent.getStringArrayExtra("dateArray", )
        val countArray = intent.getStringArrayExtra("countArray", )
        var currentTimeString = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())

        binding.calendarView.setTileSizeDp(50)

        if (dateArray != null && countArray != null) {
            var index = 0
            for(date in dateArray){
                val splitDate = date.split("-")
                val year = splitDate[0].toInt()
                val month = splitDate[1].toInt()-1
                val date = splitDate[2].toInt()

                binding.calendarView.addDecorator(SetTextDecorator(
                    Collections.singleton(CalendarDay.from(year, month, date)),
                    countArray[index]
                ))
                index++
            }
        }

        todoViewModel = ViewModelProvider(this, ViewModelFactory(currentTimeString)).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            calendarAdapter.update(it)
        }

        calendarAdapter = CalendarAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = calendarAdapter

        binding.calendarView.setDateSelected(currentCalendar, true)
        binding.calendarView.addDecorator(TodayDecorator())
        binding.calendarView.addDecorator(SundayDecoraotr())
        binding.calendarView.addDecorator(SaturdayDecorator())
    }
}