package com.conghau.car_maintenanceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val vehicleDao: VehicleDao) : ViewModel() {

    // LiveData to observe the vehicles
    val vehicles: LiveData<List<Vehicle>> = vehicleDao.getAll()

    // LiveData to hold the current selected vehicle
    private val _currentVehicle = MutableLiveData<Vehicle?>()
    val currentVehicle: LiveData<Vehicle?> = _currentVehicle

    fun setCurrentVehicle(vehicle: Vehicle) {
        _currentVehicle.value = vehicle
    }

    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            vehicleDao.delete(vehicle)
        }
    }

    // Add more functions to handle task addition and retrieval if necessary
}
