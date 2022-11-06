package com.example.reminder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.dao.TodoDao
import com.example.reminder.dto.Todo
import com.example.reminder.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(date: String?) : ViewModel() {
    val todoList: LiveData<MutableList<TodoDao.TodoHistory>>

    private val todoRepository: TodoRepository = TodoRepository.get()

    init {
        todoList = if (date == null){
            todoRepository.list()
        } else{
            todoRepository.listOnDate(date)
        }

    }

    fun getOne(id: Long) = todoRepository.getTodo(id)

    fun insert(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.insert(dto)
    }

    fun update(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.update(dto)
    }

    fun delete(dto: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoRepository.delete(dto)
    }
}