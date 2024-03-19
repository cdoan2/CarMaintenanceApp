package com.conghau.car_maintenanceapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class AddVehicleActivity : AppCompatActivity() {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val vehicleDao by lazy { db.vehicleDao() }
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle)

        val editTextMake = findViewById<EditText>(R.id.editTextMake)
        val editTextModel = findViewById<EditText>(R.id.editTextModel)
        val editTextYear = findViewById<EditText>(R.id.editTextYear)
        val btnSaveVehicle = findViewById<Button>(R.id.btnAddVehicle)

        btnSaveVehicle.setOnClickListener {
            val make = editTextMake.text.toString().trim()
            val model = editTextModel.text.toString().trim()
            val yearText = editTextYear.text.toString().trim()

            if (make.isNotEmpty() && model.isNotEmpty() && yearText.isNotEmpty()) {
                val year = yearText.toIntOrNull()
                if (year != null) {
                    val newVehicle = Vehicle(make = make, model = model, year = year)
                    insertVehicle(newVehicle)
                } else {
                    Toast.makeText(this, "Please enter a valid year", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertVehicle(vehicle: Vehicle) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                vehicleDao.insert(vehicle)
            }
            Toast.makeText(this@AddVehicleActivity, "Vehicle added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
