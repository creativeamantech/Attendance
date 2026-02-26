package com.company.attendanceapp.data.local.mapper

import com.company.attendanceapp.data.local.database.entities.EmployeeEntity
import com.company.attendanceapp.domain.model.Employee

fun EmployeeEntity.toDomain(): Employee {
    return Employee(
        employeeId = employeeId,
        name = name,
        email = email,
        phone = phone,
        department = department,
        role = role,
        managerId = managerId,
        shiftId = shiftId,
        locationId = locationId,
        remoteAllowed = remoteAllowed,
        probationFlag = probationFlag,
        status = status,
        profilePhotoUrl = profilePhotoUrl,
        createdAt = createdAt
    )
}

fun Employee.toEntity(): EmployeeEntity {
    return EmployeeEntity(
        employeeId = employeeId,
        name = name,
        email = email,
        phone = phone,
        department = department,
        role = role,
        managerId = managerId,
        shiftId = shiftId,
        locationId = locationId,
        remoteAllowed = remoteAllowed,
        probationFlag = probationFlag,
        status = status,
        profilePhotoUrl = profilePhotoUrl,
        createdAt = createdAt
    )
}
