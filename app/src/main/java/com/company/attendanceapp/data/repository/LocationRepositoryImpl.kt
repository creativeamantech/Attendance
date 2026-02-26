package com.company.attendanceapp.data.repository

import com.company.attendanceapp.data.local.database.dao.WorkLocationDao
import com.company.attendanceapp.data.local.mapper.toDomain
import com.company.attendanceapp.data.local.mapper.toEntity
import com.company.attendanceapp.domain.model.Resource
import com.company.attendanceapp.domain.model.WorkLocation
import com.company.attendanceapp.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepositoryImpl @Inject constructor(
    private val workLocationDao: WorkLocationDao
) : LocationRepository {

    override fun getLocationById(locationId: String): Flow<Resource<WorkLocation>> {
        return workLocationDao.getLocationById(locationId).map { entity ->
            if (entity != null) {
                Resource.Success(entity.toDomain())
            } else {
                Resource.Error("Location not found")
            }
        }
    }

    override fun getAllLocations(): Flow<Resource<List<WorkLocation>>> {
        return workLocationDao.getAllLocations().map { entities ->
            Resource.Success(entities.map { it.toDomain() })
        }
    }

    override fun addLocation(location: WorkLocation): Flow<Resource<Unit>> = kotlinx.coroutines.flow.flow {
        emit(Resource.Loading())
        try {
            workLocationDao.insertLocation(location.toEntity())
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}
