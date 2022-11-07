package com.example.reminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.Constant.Companion.ALARM_TIMER
import com.example.reminder.Constant.Companion.NOTIFICATION_ID
import com.example.reminder.adapter.TodoAdapter
import com.example.reminder.databinding.ActivityMainBinding
import com.example.reminder.dto.History
import com.example.reminder.dto.Todo
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.receiver.AlarmReceiver
import com.example.reminder.viewmodel.HistoryViewModel
import com.example.reminder.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var historyViewModel: HistoryViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId) {
            R.id.btnSetting -> {
                requestActivity.launch(Intent(this, SettingActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            NOTIFICATION_ID,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 0)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        // today
        binding.tvToday.text = SimpleDateFormat("yyyy년 MM월 dd일").format(System.currentTimeMillis())

        todoViewModel = ViewModelProvider(this, ViewModelFactory(null)).get(TodoViewModel::class.java)
        historyViewModel =  ViewModelProvider(this)[HistoryViewModel::class.java]

        binding.imgBtnAdd.setOnClickListener{
            requestActivity.launch(Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            })
        }
        binding.imgBtnCalendar.setOnClickListener{
            requestActivity.launch(Intent(this, CalendarActivity::class.java))
        }

        todoViewModel.todoList.observe(this){
            todoAdapter.update(it)
            if(it.size > 0){
                binding.rvTodoList.visibility=View.VISIBLE
                binding.tvRecycleEmpty.visibility=View.INVISIBLE
            }
        }

        todoAdapter = TodoAdapter(this)
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.adapter = todoAdapter

        todoAdapter.setItemClickListener(object: TodoAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)
                    requestActivity.launch(Intent(this@MainActivity, EditTodoActivity::class.java).apply {
                        putExtra("type", "EDIT")
                        putExtra("item", todo)
                    })
                }
            }
        })
        todoAdapter.setItemBtnClearClickListener(object: TodoAdapter.ItemBtnClearClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val date = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                    var history = historyViewModel.getHistory(itemId, date)
                    if (history == null) {
                        historyViewModel.insert(History(0, itemId, true, date))
                    }
                }
            }
        })
        todoAdapter.setItemBtnClearCancelClickListener(object: TodoAdapter.ItemBtnClearCancelClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val date = SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis())
                    var history = historyViewModel.getHistory(itemId, date)
                    historyViewModel.delete(history)
                }
            }
        })
        todoAdapter.setItemBtnDelayClickListener(object: TodoAdapter.ItemBtnDelayClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                val builder = AlertDialog.Builder(this@MainActivity)
                    .setTitle("할 일 미루기")
                    .setMessage("해당 일의 모든 일정이 하루 미뤄집니다.\n정말로 미루겠습니까?")
                    .setPositiveButton("미루기",
                        DialogInterface.OnClickListener{ dialog, which ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val todo = todoViewModel.getOne(itemId)
                                val cal = Calendar.getInstance()
                                cal.add(Calendar.DATE, 2)
                                cal.add(Calendar.DATE, -todo!!.repeat)
                                val newStartedAt = "%d-%02d-%02d".format(
                                    cal.get(Calendar.YEAR),
                                    cal.get(Calendar.MONTH)+1,
                                    cal.get(Calendar.DATE)
                                )
                                val newTodo = Todo(
                                    0,
                                    todo!!.title,
                                    newStartedAt,
                                    todo!!.repeat,
                                    todo!!.content,
                                    todo!!.delay+1,
                                    java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis()),
                                    null
                                )
                                val updateTodo = Todo(
                                    todo!!.id,
                                    todo!!.title,
                                    todo!!.started_at,
                                    todo!!.repeat,
                                    todo!!.content,
                                    todo!!.delay,
                                    todo!!.created_at,
                                    java.text.SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),
                                )
                                todoViewModel.insert(newTodo)
                                todoViewModel.update(updateTodo)
                            }
                            finish() //인텐트 종료

                            overridePendingTransition(0, 0)
                            startActivity(Intent(this@MainActivity, MainActivity::class.java))
                            overridePendingTransition(0, 0) //인텐트 효과 없애기
                            Toast.makeText(this@MainActivity, "미뤄졌습니다.", Toast.LENGTH_SHORT).show()
                        })
                    .setNegativeButton("안 미루기",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(this@MainActivity, "잘했습니다.", Toast.LENGTH_SHORT).show()
                        })
                builder.show()
            }
        })
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == RESULT_OK){
            val todo = it.data?.getSerializableExtra("todo") as Todo

            when(it.data?.getIntExtra("flag",-1)){
                0->{
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.insert(todo)
                    }
                    Toast.makeText(this, "추가되었습니다", Toast.LENGTH_SHORT).show()
                }
                1->{
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.update(todo)
                    }
                    Toast.makeText(this, "수정되었습니다.", Toast.LENGTH_SHORT).show()
                }
                2->{
                    CoroutineScope(Dispatchers.IO).launch {
                        todoViewModel.update(todo)
                    }
                    Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}