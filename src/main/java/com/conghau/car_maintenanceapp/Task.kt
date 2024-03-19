package com.conghau.car_maintenanceapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    val vehicleId: Int,
    val taskName: String,
    val dueDate: String
)

