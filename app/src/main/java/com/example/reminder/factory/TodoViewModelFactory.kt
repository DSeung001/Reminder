package com.example.reminder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reminder.viewmodel.TodoViewModel


class TodoViewModelFactory(private val date: String?) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return TodoViewModel(date) as T
    }
}