package com.conghau.car_maintenanceapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VehicleAdapter(
    private var vehicles: List<Vehicle> = listOf(),
    private val onVehicleSelected: (Int) -> Unit, // Add this callback for when a vehicle is selected
    private val onDeleteClick: (Vehicle) -> Unit,

) : RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false)
        return VehicleViewHolder(view, onVehicleSelected, onDeleteClick)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    override fun getItemCount() = vehicles.size

    fun setVehicles(vehicles: List<Vehicle>) {
        this.vehicles = vehicles
        notifyDataSetChanged() // Notify the adapter to refresh the list
    }

    class VehicleViewHolder(
        view: View,
        private val onVehicleSelected: (Int) -> Unit, // Add this callback for when a vehicle is selected
        private val onDeleteClick: (Vehicle) -> Unit
    ) : RecyclerView.ViewHolder(view) {
        private val makeTextView: TextView = view.findViewById(R.id.makeTextView)
        private val modelTextView: TextView = view.findViewById(R.id.modelTextView)
        private val yearTextView: TextView = view.findViewById(R.id.yearTextView)
        private val deleteButton: ImageView = view.findViewById(R.id.deleteButton)

        fun bind(vehicle: Vehicle) {
            makeTextView.text = vehicle.make
            modelTextView.text = vehicle.model
            yearTextView.text = vehicle.year.toString()

            itemView.setOnClickListener {
                onVehicleSelected(vehicle.id)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(vehicle)
            }
        }
    }
}
