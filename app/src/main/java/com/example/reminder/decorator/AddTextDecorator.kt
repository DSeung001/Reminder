package com.example.reminder.decorator

import android.util.Log
import com.example.reminder.decorator.span.AddTextToDates
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashSet

class AddTextDecorator (dates: List<CalendarDay>, countMap:Map<String,String>) : DayViewDecorator {

    private val dates: HashSet<CalendarDay> = HashSet(dates)
    private val countMap = countMap

    private var calendarString = ""

    // 파라미터를
    override fun shouldDecorate(day: CalendarDay): Boolean {
        calendarString = SimpleDateFormat("yyyy-MM-dd").format(day.calendar.time)

        Log.d("test", "shouldDecorate "+calendarString)
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        Log.d("test", "decorate "+calendarString)
        view?.addSpan(AddTextToDates(countMap[calendarString]+"건"))
    }
}