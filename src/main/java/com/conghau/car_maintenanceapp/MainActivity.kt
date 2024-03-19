package com.conghau.car_maintenanceapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private val vehicleDao by lazy { AppDatabase.getDatabase(this).vehicleDao() }

    // Selected vehicle ID
    private var selectedVehicleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecyclerView()
        setupAddTaskButton()
        setupAddVehicleButton()
        observeVehicleList()
    }

    private fun initializeRecyclerView() {
        recyclerView = findViewById(R.id.maintenanceRecyclerView)
        vehicleAdapter = VehicleAdapter(onVehicleSelected = { vehicleId ->
            selectedVehicleId = vehicleId // Save the selected vehicle ID
        }, onDeleteClick = { vehicle ->
            deleteVehicle(vehicle)
        })
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = vehicleAdapter
        }
    }

    private fun deleteVehicle(vehicle: Vehicle) {
        // Launch a coroutine in the lifecycle scope of MainActivity
        lifecycleScope.launch {
            // Execute the database operation on the IO dispatcher
            withContext(Dispatchers.IO) {
                vehicleDao.delete(vehicle)
            }
            // Optionally, you can show a confirmation message on the main thread
            // after the vehicle is deleted
            Toast.makeText(this@MainActivity, "Vehicle deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupAddTaskButton() {
        fab = findViewById(R.id.addTaskFab)
        fab.setOnClickListener {
            selectedVehicleId?.let { vehicleId ->
                // Pass the selected vehicle ID to the AddTaskActivity
                val intent = Intent(this, AddTaskActivity::class.java).apply {
                    putExtra("VEHICLE_ID", vehicleId)
                }
                startActivity(intent)
            } ?: run {
                // Inform the user to select a vehicle first
                // This can be a Toast, Snackbar, or a dialog
            }
        }
    }

    private fun setupAddVehicleButton() {
        val btnAddVehicle: Button = findViewById(R.id.btnAddVehicle)
        btnAddVehicle.setOnClickListener {
            startActivity(Intent(this, AddVehicleActivity::class.java))
        }
    }

    private fun observeVehicleList() {
        vehicleDao.getAll().observe(this, Observer { vehicles ->
            vehicleAdapter.setVehicles(vehicles)
        })
    }
}
