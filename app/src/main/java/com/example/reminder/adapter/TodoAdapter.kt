package com.example.reminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reminder.R
import com.example.reminder.dao.TodoDao

class TodoAdapter(val context: Context):RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var list = mutableListOf<TodoDao.TodoHistory>()
    private lateinit var itemCheckBoxClickListener: ItemCheckBoxClickListener
    private lateinit var itemClickListener: ItemClickListener
    private lateinit var itemBtnClearClickListener: ItemBtnClearClickListener
    private lateinit var itemBtnClearCancelClickListener: ItemBtnClearCancelClickListener
    private lateinit var itemBtnDelayClickListener: ItemBtnDelayClickListener

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.tvTodoItem)
        var btnClear = itemView.findViewById<Button>(R.id.btnClear)
        var btnClearCancel = itemView.findViewById<Button>(R.id.btnClearCancel)
        var btnDelay = itemView.findViewById<Button>(R.id.btnDelay)
        var imgClear = itemView.findViewById<ImageView>(R.id.imgClear)

        fun onBind(data: TodoDao.TodoHistory){
            title.text = data.title
            itemView.setOnClickListener{
                itemClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
            if(data.result == true){
                imgClear.visibility = View.VISIBLE
                btnClearCancel.visibility = View.VISIBLE
                btnClear.visibility = View.INVISIBLE
                btnDelay.visibility = View.INVISIBLE
            }
            btnClearCancel.setOnClickListener {
                imgClear.visibility = View.INVISIBLE
                btnClearCancel.visibility = View.INVISIBLE
                btnClear.visibility = View.VISIBLE
                btnDelay.visibility = View.VISIBLE
                itemBtnClearCancelClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
            }
            btnClear.setOnClickListener{
                imgClear.visibility = View.VISIBLE
                btnClearCancel.visibility = View.VISIBLE
                btnClear.visibility = View.INVISIBLE
                btnDelay.visibility = View.INVISIBLE
                itemBtnClearClickListener.onClick(it, layoutPosition, list[layoutPosition].id)
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

    fun update(newList: MutableList<TodoDao.TodoHistory>){
        this.list = newList
        notifyDataSetChanged()
    }

    interface ItemCheckBoxClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemBtnClearClickListener{
        fun onClick(view: View, position: Int, itemId: Long)
    }
    interface ItemBtnClearCancelClickListener{
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
    fun setItemBtnClearClickListener(itemBtnClearClickListener: ItemBtnClearClickListener){
        this.itemBtnClearClickListener = itemBtnClearClickListener
    }
    fun setItemBtnClearCancelClickListener(itemBtnClearCancelClickListener: ItemBtnClearCancelClickListener){
        this.itemBtnClearCancelClickListener = itemBtnClearCancelClickListener
    }
    fun setItemBtnDelayClickListener(itemBtnDelayClickListener: ItemBtnDelayClickListener){
        this.itemBtnDelayClickListener = itemBtnDelayClickListener
    }
}