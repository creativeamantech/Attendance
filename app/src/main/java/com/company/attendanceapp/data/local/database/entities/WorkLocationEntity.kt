package com.company.attendanceapp.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.attendanceapp.domain.model.LocationType

@Entity(tableName = "work_locations")
data class WorkLocationEntity(
    @PrimaryKey val locationId: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val radiusMeters: Double,
    val address: String,
    val city: String,
    val timezone: String,
    val locationType: LocationType,
    val isActive: Boolean
)
