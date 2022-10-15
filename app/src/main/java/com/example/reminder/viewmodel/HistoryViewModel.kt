package com.example.reminder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminder.dto.History
import com.example.reminder.repository.HistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {
    private val historyRepository: HistoryRepository = HistoryRepository.get()

    fun insert(dto: History) = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.insert(dto)
    }

    fun update(dto: History) = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.update(dto)
    }

    fun delete(dto: History) = viewModelScope.launch(Dispatchers.IO) {
        historyRepository.delete(dto)
    }

    fun getHistory(todoId: Long, settingOn: String) = historyRepository.getHistory(todoId, settingOn)
}