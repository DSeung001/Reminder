package com.example.reminder

import android.app.AlarmManager
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import com.example.reminder.databinding.ActivitySettingBinding
import com.example.reminder.dto.Setting
import com.example.reminder.dto.Todo
import com.example.reminder.repository.SettingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    val settingRepository = SettingRepository.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val setting = intent.getSerializableExtra("setting") as Setting?

        if (setting != null) {
            if(setting.auto_delay == 1){
                binding.switchAutoDelay.toggle()
           }
            binding.btnTimeSelect.setText(setting.alarm_time)
        }

        binding.btnTimeSelect.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val timeString = "%02d:%02d".format(hourOfDay, minute)
                binding.btnTimeSelect.setText(timeString)

                CoroutineScope(Dispatchers.IO).launch {
                    settingUpdate()
                    // alarm resetting
                    val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
                    AlarmSetting().reSetting(this@SettingActivity, alarmManager)
                }
            }

            TimePickerDialog(this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true).show()
        }

        binding.switchAutoDelay.setOnCheckedChangeListener{ view, isChecked ->
            CoroutineScope(Dispatchers.IO).launch {
                settingUpdate()
            }
        }
    }

    suspend fun settingUpdate(){
        val time = binding.btnTimeSelect.text.toString()
        var autoDelay = 0

        if (binding.switchAutoDelay.isChecked) {
            autoDelay = 1
        } else {
            autoDelay = 0
        }

        val newSetting = Setting(1, time, autoDelay);

        if (settingRepository.getSetting(1) != null) {
            settingRepository.update(newSetting)
        } else {
            settingRepository.insert(newSetting)
        }
    }
}