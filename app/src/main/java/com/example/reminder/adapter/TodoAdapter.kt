package com.example.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.dto.Todo
import com.example.reminder.viewmodel.HistoryViewModel

class TodoAdapter(val context: Context):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var list = mutableListOf<Todo>()
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    private lateinit var itemClickListener: ItemClickListener
    private lateinit var itemBtnClerClickListener: ItemBtnClerClickListener
    private lateinit var itemBtnDelayClickListener: ItemBtnDelayClickListener

    lateinit var historyViewModel: HistoryViewModel

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.tvTodoItem)
        var btnClear = itemView.findViewById<Button>(R.id.btnClear)
        var btnDelay = itemView.findViewById<Button>(R.id.btnDelay)
        var imgClear = itemView.findViewById<ImageView>(R.id.imgClear)

        fun onBind(data: Todo){
            title.text = data.title
            itemView.setOnClickListener{
                itemClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
//            if(data.result == true){
//                imgClear.visibility = View.VISIBLE
//            }
            btnClear.setOnClickListener{
                if(imgClear.isVisible){
                    //delete해야함
                    imgClear.visibility = View.INVISIBLE
                }else{
                    imgClear.visibility = View.VISIBLE
                }
                itemBtnClerClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
            btnDelay.setOnClickListener {
                itemBtnDelayClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
        }
    }

    // item을 가져와서 inflate를 해줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list, parent, false)
        return TodoViewHolder(view)
    }
    // onCreateViewHolder에서 가져와서 view에 실제 데이터 연결
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        holder.onBind(list[position])
    }
    // item의 총 갯수
    override fun getItemCount(): Int {
        if(list == null)
            return  0
        return list.size
    }

    fun update(newList: MutableList<Todo>){
        this.list = newList
        notifyDataSetChanged()
    }

    interface ItemCheckBoxClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemBtnClerClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemBtnDelayClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }

    fun setItemCheckBoxClickListener(itemCheckBoxClickListener: ItemCheckBoxClickListener) {
        this.itemCheckBoxClickListener = itemCheckBoxClickListener
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }
    fun setItemBtnClearClickListener(itemBtnClerClickListener: ItemBtnClerClickListener){
        this.itemBtnClerClickListener = itemBtnClerClickListener
    }
    fun setItemBtnDelayClickListener(itemBtnDelayClickListener: ItemBtnDelayClickListener){
        this.itemBtnDelayClickListener = itemBtnDelayClickListener
    }
}