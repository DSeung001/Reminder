package com.example.reminder

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.CalendarAdapter
import com.example.reminder.databinding.ActivityCalendarBinding
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.viewmodel.TodoViewModel
import java.util.Calendar

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // actionbar color chagne
        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#303030"))
        actionBar!!.setBackgroundDrawable(colorDrawable)

        var date = intent.getStringExtra("date")
        var timeInMillis = intent.getLongExtra("timeInMillis", 0)
        if (timeInMillis > 0){
            binding.calendarView.setDate(timeInMillis)
        }

        todoViewModel = ViewModelProvider(this, ViewModelFactory(date)).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            calendarAdapter.update(it)
        }

        calendarAdapter = CalendarAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = calendarAdapter

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            var date = String.format("%04d-%02d-%02d", year, month+1, dayOfMonth)
            var calendar:Calendar = Calendar.getInstance()
            calendar.set(year,month,dayOfMonth)

            finish()
            overridePendingTransition(0, 0)

            val newIntent = Intent(this, CalendarActivity::class.java)
            newIntent.putExtra("timeInMillis", calendar.timeInMillis)
            newIntent.putExtra("date", date);
            startActivity(newIntent)

            overridePendingTransition(0, 0)

        }
    }
}