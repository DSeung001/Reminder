package com.example.reminder

import android.app.AlarmManager
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.reminder.databinding.ActivitySettingBinding
import com.example.reminder.dto.Option
import com.example.reminder.repository.OptionRepository
import com.example.reminder.setting.AlarmSetting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class OptionActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    lateinit var alarmTime: Option
    lateinit var autoDelay: Option

    val optionRepository = OptionRepository.get()

    // alarm time picker event
    val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        val timeString = "%02d:%02d".format(hourOfDay, minute)
        binding.btnTimeSelect.setText(timeString)

        CoroutineScope(Dispatchers.IO).launch {
            alarmTime.option_value = timeString
            optionRepository.update(alarmTime)

            // alarm resetting
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            AlarmSetting().setting(this@OptionActivity, alarmManager)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alarmTime = intent.getSerializableExtra("alarmTime") as Option
        autoDelay = intent.getSerializableExtra("autoDelay") as Option

        if (autoDelay.option_value == "true"){
            binding.switchAutoDelay.toggle()
        }
        binding.btnTimeSelect.setText(alarmTime.option_value)

        binding.btnTimeSelect.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true).show()
        }

        binding.switchAutoDelay.setOnCheckedChangeListener{ view, isChecked ->
            CoroutineScope(Dispatchers.IO).launch {
                autoDelay.option_value = if(isChecked) "true" else "false"
                optionRepository.update(autoDelay)
            }
        }
    }
}