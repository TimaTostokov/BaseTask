package com.example.my.basetask.presentation.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.my.basetask.R
import com.example.my.basetask.databinding.ItemTaskBinding
import com.example.my.basetask.model.TaskModel


class MainAdapter(
    private val onClickItem: (task: TaskModel) -> Unit,
    private val onLongClickItem: (task: TaskModel) -> Unit,
    private val updateTask: (task: TaskModel) -> Unit
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val _tasks = mutableListOf<TaskModel>()
    private val tasks get() = _tasks

    fun addDataInList(taskList: List<TaskModel>) {
        _tasks.clear()
        _tasks.addAll(taskList)
        notifyItemRangeInserted(_tasks.size, taskList.size - _tasks.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("ResourceAsColor")
        fun bind(taskModel: TaskModel) {
            binding.cbTask.text = taskModel.title
            binding.cbTask.isChecked = taskModel.state
            if (binding.cbTask.isChecked) {
                binding.cbTask.setTextColor(R.color.grey)
            }
            initListeners(taskModel)
        }

        private fun initListeners(taskModel: TaskModel) {
            binding.btnUpdateTask.setOnClickListener {
                updateTask(taskModel)
            }
            itemView.setOnClickListener { onClickItem(taskModel) }
            itemView.setOnLongClickListener {
                onLongClickItem(taskModel)
                true
            }
        }
    }

}