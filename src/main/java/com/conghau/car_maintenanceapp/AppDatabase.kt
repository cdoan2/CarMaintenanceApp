package com.conghau.car_maintenanceapp

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [Vehicle::class, Task::class], version = 2) // Increase version number
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "car_maintenance_database"
                ).fallbackToDestructiveMigration() // Enable destructive migration
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
