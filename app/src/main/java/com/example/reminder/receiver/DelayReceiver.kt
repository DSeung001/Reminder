package com.example.reminder.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.reminder.dto.Todo
import com.example.reminder.repository.OptionRepository
import com.example.reminder.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DelayReceiver : BroadcastReceiver() {

    private val optionRepository = OptionRepository.get()
    private val todoRepository = TodoRepository.get()

    override fun onReceive(context: Context, intent: Intent) {
        notFinishDelay()
    }

    @SuppressLint("SimpleDateFormat")
    private fun notFinishDelay (){
        Log.d("test", "DelayReceiver, notFinishDelay")
        CoroutineScope(Dispatchers.IO).launch {
            val autoDelay = optionRepository.getSettingByOptionName("auto_delay")
            Log.d("test", "autoDelay : "+autoDelay.option_value)
            if(autoDelay.option_value == "true"){
                Log.d("test", "DelayReceiver, condition")
                val calendar: Calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)

                // 조건문은 통과하는데 아래 리스트가 하나도 나오지 않아서 막힌듯
                val list = todoRepository.toDelayList(SimpleDateFormat("yyyy-MM-dd").format(calendar.time))
                Log.d("test", "size : "+list.size)
                for (todo in list) {
                    Log.d("test", "DelayReceiver, todo title "+todo.title)
                    val cal = Calendar.getInstance()
                    cal.add(Calendar.DATE, 1)
                    cal.add(Calendar.DATE, -todo.repeat)
                    val newStartedAt = "%d-%02d-%02d".format(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH)+1,
                        cal.get(Calendar.DATE)
                    )
                    val newTodo = Todo(
                        0,
                        todo.title,
                        newStartedAt,
                        todo.repeat,
                        todo.content,
                        todo.delay+1,
                        SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis()),
                        null
                    )
                    val updateTodo = Todo(
                        todo.id,
                        todo.title,
                        todo.started_at,
                        todo.repeat,
                        todo.content,
                        todo.delay,
                        todo.created_at,
                        SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                    )
                    todoRepository.insert(newTodo)
                    todoRepository.update(updateTodo)
                }
            }
        }
    }
}