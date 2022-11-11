package com.example.reminder.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.reminder.viewmodel.TodoViewModel
import java.text.SimpleDateFormat


class ViewModelFactory(date: String?): ViewModelProvider.Factory {
    private var date: String = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())

    init {
        if (date != null){
            this.date = date
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
            return TodoViewModel(date) as T
        }
        throw IllegalArgumentException("뷰모델을 만들 수 없습니다 : IllegalArgumentException")
    }
}