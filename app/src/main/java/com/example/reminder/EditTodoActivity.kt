package com.example.reminder

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reminder.databinding.ActivityEditTodoBinding
import com.example.reminder.dto.Todo
import java.text.SimpleDateFormat
import java.util.*

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTodoBinding

    private var todo: Todo?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        binding.btnTodoStartedAt.setOnClickListener{
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                val dateString = "%d-%02d-%02d".format(year,month+1,dayOfMonth)
                binding.btnTodoStartedAt.setText(dateString)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE)).show()
        }

        if(type.equals("ADD")){
            binding.btnSave.text = "추가하기"
        } else {
            todo = intent.getSerializableExtra("item") as Todo?
            binding.etTodoTitle.setText(todo!!.title)
            binding.btnTodoStartedAt.setText(todo!!.started_at)
            binding.etTodoRepeat.setText(todo!!.repeat.toString())
            binding.etTodoContent.setText(todo!!.content)
            binding.btnSave.text = "수정하기"
        }

        binding.btnSave.setOnClickListener{
            val title = binding.etTodoTitle.text.toString()
            val startedAt = binding.btnTodoStartedAt.text.toString();
            val repeat = binding.etTodoRepeat.text.toString()
            val content = binding.etTodoContent.text.toString()
            val createdAt = SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

            if(type.equals("ADD")){
                if(repeat == "0" ){
                    Toast.makeText(this, "0을 주기로 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                } else{
                    if(title.isNotEmpty() && repeat.isNotEmpty() && content.isNotEmpty()){
                        val todo = Todo(0, title, startedAt, repeat.toInt(), content, 0, createdAt, null)
                        val intent = Intent().apply {
                            putExtra("todo", todo)
                            putExtra("flag", 0)
                        }
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            } else {
                if(title.isNotEmpty() && repeat.isNotEmpty() && content.isNotEmpty()){
                    if(repeat == "0" ){
                        Toast.makeText(this, "0을 주기로 할 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } else{
                        val todo = Todo(todo!!.id, title, startedAt, repeat.toInt(), content, todo!!.delay, todo!!.created_at, todo!!.expired_at)
                        val intent = Intent().apply {
                            putExtra("todo", todo)
                            putExtra("flag", 1)
                        }
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }
    }
}