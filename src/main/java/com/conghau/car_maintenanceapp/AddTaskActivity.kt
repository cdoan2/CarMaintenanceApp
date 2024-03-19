package com.conghau.car_maintenanceapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_task)

        // Obtain the vehicle ID from the intent
        val vehicleId = intent.getIntExtra("VEHICLE_ID", -1)

        // TODO: Setup your views and handle task creation here
    }
}
