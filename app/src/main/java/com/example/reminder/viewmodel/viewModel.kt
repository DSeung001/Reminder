package com.example.reminder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.dto.Todo
import com.example.reminder.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel : ViewModel() {
    val todoList: LiveData<MutableList<Todo>>
    private val todoRepository: TodoRepository = TodoRepository.get()

    init {
        todoList = todoRepository.list()
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