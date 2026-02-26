package com.company.attendanceapp.data.local.mapper

import com.company.attendanceapp.data.local.database.entities.WorkLocationEntity
import com.company.attendanceapp.domain.model.WorkLocation

fun WorkLocationEntity.toDomain(): WorkLocation {
    return WorkLocation(
        locationId = locationId,
        locationName = locationName,
        latitude = latitude,
        longitude = longitude,
        radiusMeters = radiusMeters,
        address = address,
        city = city,
        timezone = timezone,
        locationType = locationType,
        isActive = isActive
    )
}

fun WorkLocation.toEntity(): WorkLocationEntity {
    return WorkLocationEntity(
        locationId = locationId,
        locationName = locationName,
        latitude = latitude,
        longitude = longitude,
        radiusMeters = radiusMeters,
        address = address,
        city = city,
        timezone = timezone,
        locationType = locationType,
        isActive = isActive
    )
}
