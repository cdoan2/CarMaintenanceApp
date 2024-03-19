package com.conghau.car_maintenanceapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicle")
    fun getAll(): LiveData<List<Vehicle>>


    @Insert
    fun insert(vehicle: Vehicle)

    @Delete
    suspend fun delete(vehicle: Vehicle)

    @Insert
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM tasks WHERE vehicleId = :vehicleId")
    fun getTasksForVehicle(vehicleId: Int): LiveData<List<Task>>
}
