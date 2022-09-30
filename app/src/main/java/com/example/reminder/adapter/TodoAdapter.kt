package com.example.reminder.adapter

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.dto.Todo

class TodoAdapter(val context: Context):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var list = mutableListOf<Todo>()
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.tvTodoItem)
        var timestamp = itemView.findViewById<TextView>(R.id.tvTimeStamp)
        var checkbox = itemView.findViewById<CheckBox>(R.id.cbCheck)

        fun onBind(data:Todo){
            title.text = data.title
            timestamp.text = data.timestamp
            checkbox.isChecked = data.isChecked

            if (data.isChecked){
                title.paintFlags = title.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                title.paintFlags = title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkbox.setOnClickListener{
                itemCheckBoxClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun update(newList: MutableList<Todo>){
        this.list = newList
        notifyDataSetChanged()
    }

    interface ItemCheckBoxClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }
}