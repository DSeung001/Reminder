package com.example.reminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.reminder.databinding.ActivityEditTodoBinding
import com.example.reminder.dto.Todo
import java.text.SimpleDateFormat

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        if(type.equals("ADD")){
            binding.btnSave.text = "추가하기"
        } else {
            binding.btnSave.text = "수정하기"
        }

        binding.btnSave.setOnClickListener{

            val title = binding.etTodoTitle.text.toString()
            val content = binding.etTodoContent.text.toString()
            val currentDate = SimpleDateFormat("yyyy-MM-dd HH:mm").format(System.currentTimeMillis())

            if(type.equals("ADD")){
                if(title.isNotEmpty() && content.isNotEmpty()){
                    val todo = Todo(0, title, content, currentDate, false)
                    val intent = Intent().apply {
                        putExtra("todo", todo)
                        putExtra("flag", 0)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                }
            } else {

            }
        }
    }
}