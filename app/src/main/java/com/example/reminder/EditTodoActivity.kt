package com.example.reminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reminder.databinding.ActivityEditTodoBinding

class EditTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_edit_todo)

        val type = intent.getStringExtra("type")

        if(type.equals("ADD")){
            binding.btnSave.text = "추가하기"
        } else {
            binding.btnSave.text = "수정하기"
        }
    }
}