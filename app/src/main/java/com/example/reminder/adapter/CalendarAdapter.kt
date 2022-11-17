package com.example.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.dao.TodoDao

class CalendarAdapter(val context: Context):RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private var list = mutableListOf<TodoDao.TodoHistory>()

    inner class CalendarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title = itemView.findViewById<TextView>(R.id.tvTodoItem)
        val imgClear = itemView.findViewById<ImageView>(R.id.imgClear)
        val tvDelay = itemView.findViewById<TextView>(R.id.tvDelay)

        fun onBind(data:TodoDao.TodoHistory){
            title.text = data.title
            if (data.result == true) {
                imgClear.visibility = View.VISIBLE
            } else {
                imgClear.visibility = View.INVISIBLE
            }
            tvDelay.text = "미룬 횟수 : "+data.delay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_calendar_list, parent, false)
        return CalendarViewHolder(view)
    }
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int){
        holder.onBind(list[position])
    }
    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: MutableList<TodoDao.TodoHistory>){
        this.list = newList
        notifyDataSetChanged()
    }
}