package com.example.reminder.decorator.span

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan

class AddTextToDates(text: String) : LineBackgroundSpan {

    private var dayPrice = text

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lnum: Int
    ) {
        canvas.drawText(dayPrice,((left+right)/3).toFloat(),(bottom+25).toFloat(),paint)
    }
}
