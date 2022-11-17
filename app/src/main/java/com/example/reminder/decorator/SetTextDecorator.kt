package com.example.reminder.decorator

import com.example.reminder.decorator.span.AddTextToDates
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class SetTextDecorator(dates: Collection<CalendarDay>, count:String): DayViewDecorator {

    var dates: HashSet<CalendarDay> = HashSet(dates)
    var count = count

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(AddTextToDates(count+"건"))

    }
}