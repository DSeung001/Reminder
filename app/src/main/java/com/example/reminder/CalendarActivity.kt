package com.example.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.TodoAdapter
import com.example.reminder.databinding.ActivityCalendarBinding
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.viewmodel.TodoViewModel

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this, ViewModelFactory(null)).get(TodoViewModel::class.java)

        todoViewModel.todoList.observe(this){
            todoAdapter.update(it)
        }

        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        binding.calendarView.setOnDateChangeListener { view, year, month, day ->
            val date = String.format("%d-%d-%d", year, month+1, day)

            todoViewModel = ViewModelProvider(this, ViewModelFactory(date)).get(TodoViewModel::class.java)
            todoAdapter = TodoAdapter(this)
            todoViewModel.todoList.observe(this){
                todoAdapter.update(it)
            }
            binding.rvTodoList.adapter = todoAdapter
        }

    }


}