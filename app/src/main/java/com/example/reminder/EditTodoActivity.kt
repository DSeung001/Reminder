package com.example.reminder

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
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

        // actionbar color chagne
        val actionBar: ActionBar?
        actionBar = supportActionBar
        val colorDrawable = ColorDrawable(Color.parseColor("#303030"))
        actionBar!!.setBackgroundDrawable(colorDrawable)

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
            binding.etTodoRepeat.isEnabled = false
            binding.etTodoContent.setText(todo!!.content)
            binding.btnSave.text = "수정하기"
            binding.btnDelete.visibility = View.VISIBLE
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
                } else if (startedAt == "시작일을 선택해주세요."){
                    Toast.makeText(this, "시작일을 선택해주세요.", Toast.LENGTH_SHORT).show()
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

        binding.btnDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("해당 일을 정말 삭제하겠습니까?")
                .setMessage("결심했을 때를 생각해주세요!")
                .setPositiveButton("삭제",
                DialogInterface.OnClickListener{
                    dialog, id ->
                    val intent = Intent().apply {
                        val todo = Todo(todo!!.id, todo!!.title, todo!!.started_at, todo!!.repeat, todo!!.content, todo!!.delay, todo!!.created_at, java.text.SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()),)
                        putExtra("todo", todo)
                        putExtra("flag", 2)
                    }
                    setResult(RESULT_OK, intent)
                    finish()
                })
                .setNegativeButton("취소",
                DialogInterface.OnClickListener{
                    dialog, id ->
                })
            builder.show()
        }
    }
}