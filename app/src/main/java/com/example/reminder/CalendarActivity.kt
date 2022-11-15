package com.example.reminder

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.CalendarAdapter
import com.example.reminder.databinding.ActivityCalendarBinding
import com.example.reminder.decorator.*
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.repository.TodoRepository
import com.example.reminder.viewmodel.TodoViewModel
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var calendarAdapter: CalendarAdapter

    private val todoRepository: TodoRepository = TodoRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val timeInMillis = intent.getLongExtra("timeInMillis", 0)
        val currentCalendar:Calendar = Calendar.getInstance()
        var currentTimeString = Calendar.DATE.toString()

        if (timeInMillis > 0){
            currentCalendar.setTimeInMillis(timeInMillis)
            currentTimeString = SimpleDateFormat("yyyy-MM-dd").format(currentCalendar.time)
        }
        binding.calendarView.setDateSelected(currentCalendar, true)
        binding.calendarView.addDecorator(SundayDecoraotr())
        binding.calendarView.addDecorator(SaturdayDecorator())

        todoViewModel = ViewModelProvider(this, ViewModelFactory(currentTimeString)).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            calendarAdapter.update(it)
        }

        calendarAdapter = CalendarAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = calendarAdapter

        binding.calendarView.addDecorator(TodayDecorator())

        binding.calendarView.setOnDateChangedListener{ widget, date, selected ->
            val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date.calendar.time)

            todoViewModel.reInit(selectedDate)
            todoViewModel.todoList.observe(this){
                calendarAdapter.update(it)
            }

//            binding.calendarView.addDecorator(HighlightingDecorator())
            binding.calendarView.addDecorator(HighlightingDecorator(Collections.singleton(date)))

        }

        binding.calendarView.setOnMonthChangedListener { widget, date ->
            
            val lastDay = date.calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            
            
//            Log.d("test", dates.size.toString())

            for (re in 1..3) {
                CoroutineScope(Dispatchers.IO).launch {
                    val repeatDay =
                        SimpleDateFormat("yyyy-MM-").format(date.calendar.time) + ("%02d".format(re))
                    val repeatCalendarDay = CalendarDay.from(date.year, date.month, re)
                    val todoCount = todoRepository.getCount(repeatDay)

                    if (todoCount.isNotEmpty() && todoCount[0].count.toInt() > 0) {
                        Log.d("test", repeatDay+" "+todoCount.toString())
                        val checkCalendarDay = CalendarDay.from(date.year, date.month, re)
//                        binding.calendarView.addDecorator(HighlightingDecorator(Collections.singleton(checkCalendarDay)))


//                        binding.calendarView.addDecorator(HighlightingDecorator())

//                        Log.d("test",
//                            repeatCalendarDay.toString() + ", " + todoCount[0].count.toInt()
//                                .toString()
//                        )
//                        dates += repeatCalendarDay
                    }
                }
//                Handler(Looper.getMainLooper()).postDelayed({
//                    binding.calendarView!!.removeDecorators()
//                    binding.calendarView!!.invalidateDecorators()
//                }, 0)
            }

//            Log.d("test", "size : "+dates.size.toString())
//            binding.calendarView.addDecorator(HighlightingDecorator(Collections.singleton(repeatCalendarDay)))


//                Log.d("test", dates.size.toString())
//            Log.d("test", dates.size.toString())



        }
    }
}