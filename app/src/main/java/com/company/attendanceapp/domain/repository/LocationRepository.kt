package com.company.attendanceapp.domain.repository

import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.model.WorkLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getLocationById(locationId: String): Flow<Resource<WorkLocation>>
    fun getAllLocations(): Flow<Resource<List<WorkLocation>>>
    fun addLocation(location: WorkLocation): Flow<Resource<Unit>>
}
