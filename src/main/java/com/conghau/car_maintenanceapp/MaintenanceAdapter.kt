package com.conghau.car_maintenanceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MaintenanceAdapter(
    private var tasks: MutableList<MaintenanceTask>,
    private val onClick: (MaintenanceTask) -> Unit
) : RecyclerView.Adapter<MaintenanceAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_maintenance_task, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)
        holder.itemView.setOnClickListener { onClick(task) }
    }

    override fun getItemCount() = tasks.size

    fun setTasks(tasks: MutableList<MaintenanceTask>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskNameView: TextView = itemView.findViewById(R.id.taskNameTextView)
        private val taskDueDateView: TextView = itemView.findViewById(R.id.taskDueDateTextView)

        fun bind(task: MaintenanceTask) {
            taskNameView.text = task.taskName
            taskDueDateView.text = task.dueDate
        }
    }
}
