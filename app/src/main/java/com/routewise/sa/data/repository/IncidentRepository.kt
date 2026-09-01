package com.routewise.sa.data.repository

import com.routewise.sa.data.local.IncidentDao
import com.routewise.sa.data.model.IncidentEntity
import com.routewise.sa.data.remote.datasource.IncidentRemoteDataSource
import com.routewise.sa.data.remote.dto.ReportIncidentRequest
import kotlinx.coroutines.flow.Flow

class IncidentRepository(
    private val localDao: IncidentDao,
    private val remoteSource: IncidentRemoteDataSource
) {
    fun getIncidents(province: String?): Flow<List<IncidentEntity>> {
        return if (province.isNullOrEmpty() || province == "All Provinces") {
            localDao.getAllIncidents()
        } else {
            localDao.getIncidentsByProvince(province)
        }
    }

    suspend fun syncRemoteIncidents(province: String?): Result<Unit> {
        val result = remoteSource.fetchIncidents(province)
        return if (result.isSuccess) {
            val entities = result.getOrNull()?.map { dto ->
                IncidentEntity(
                    id = dto.id,
                    type = dto.type,
                    roadName = dto.roadName,
                    city = dto.city,
                    province = dto.province,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    timestamp = dto.timestamp,
                    reportedBy = dto.reportedBy,
                    description = dto.description,
                    severity = dto.severity,
                    statusBox = "Active Alert",
                    upvotes = dto.upvotes
                )
            } ?: emptyList()
            localDao.insertIncidents(entities)
            Result.success(Unit)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
        }
    }

    suspend fun reportHazard(
        type: String,
        roadName: String,
        city: String,
        province: String,
        lat: Double,
        lng: Double,
        desc: String,
        severity: String,
        userId: String
    ): Result<Unit> {
        val request = ReportIncidentRequest(
            type = type,
            roadName = roadName,
            city = city,
            province = province,
            latitude = lat,
            longitude = lng,
            description = desc,
            severity = severity,
            reportedByUserId = userId
        )
        val remoteResult = remoteSource.submitReport(request)
        return if (remoteResult.isSuccess) {
            val dto = remoteResult.getOrNull()!!
            localDao.insertIncident(
                IncidentEntity(
                    id = dto.id,
                    type = dto.type,
                    roadName = dto.roadName,
                    city = dto.city,
                    province = dto.province,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    timestamp = dto.timestamp,
                    reportedBy = dto.reportedBy,
                    description = dto.description,
                    severity = dto.severity,
                    statusBox = "Active Alert",
                    upvotes = 1
                )
            )
            Result.success(Unit)
        } else {
            Result.failure(remoteResult.exceptionOrNull() ?: Exception("Report failed"))
        }
    }

    suspend fun upvote(id: String) {
        localDao.incrementUpvote(id)
        remoteSource.upvote(id)
    }
}
