package com.example.reminder

import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminder.adapter.TodoAdapter
import com.example.reminder.databinding.ActivityMainBinding
import com.example.reminder.dto.History
import com.example.reminder.dto.Todo
import com.example.reminder.factory.ViewModelFactory
import com.example.reminder.viewmodel.HistoryViewModel
import com.example.reminder.viewmodel.TodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var todoViewModel: TodoViewModel
    lateinit var historyViewModel: HistoryViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoViewModel = ViewModelProvider(this, ViewModelFactory(null)).get(TodoViewModel::class.java)
        historyViewModel =  ViewModelProvider(this)[HistoryViewModel::class.java]

        // fab, add
        binding.fabAdd.setOnClickListener{
            var intent = Intent(this, EditTodoActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }
        // fab, calendar
        binding.fabCalendar.setOnClickListener{
            var intent = Intent(this, CalendarActivity::class.java).apply {}
            requestActivity.launch(intent)
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

        todoAdapter.setItemCheckBoxClickListener(object: TodoAdapter.ItemCheckBoxClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    /*val todo = todoViewModel.getOne(itemId)
                    todo.isChecked = !todo.isChecked
                    todoViewModel.update(todo)*/
                }
            }
        })
        todoAdapter.setItemClickListener(object: TodoAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val todo = todoViewModel.getOne(itemId)
                    val intent = Intent(this@MainActivity, EditTodoActivity::class.java).apply {
                        putExtra("type", "EDIT")
                        putExtra("item", todo)
                    }
                    requestActivity.launch(intent)
                }
            }
        })
        todoAdapter.setItemBtnClearClickListener(object: TodoAdapter.ItemBtnClerClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
                    val date = LocalDate.now().toString()
                    var history = historyViewModel.getHistory(itemId, date)
                    if (history == null) {
                        historyViewModel.insert(History(0, itemId, true, date))
                    }
                }
            }
        })
        todoAdapter.setItemBtnDelayClickListener(object: TodoAdapter.ItemBtnDelayClickListener{
            override fun onClick(view: View, position: Int, itemId: Long) {
                CoroutineScope(Dispatchers.IO).launch {
//                    Toast.makeText(intent, "test.", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.menu_item_delete -> {
//                Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show()
//                todoViewModel.todoList.value!!.forEach{
//                    if (it.isChecked){
//                        todoViewModel.delete(it)
//                    }
//                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
            }
        }
    }
}