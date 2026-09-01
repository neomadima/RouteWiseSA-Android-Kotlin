package com.routewise.sa.data.remote.datasource

import com.routewise.sa.data.remote.RouteWiseApiService
import com.routewise.sa.data.remote.dto.DirectionsResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RouteRemoteDataSource(
    private val apiService: RouteWiseApiService
) {
    suspend fun fetchDirections(
        origin: String,
        destination: String,
        waypoints: List<String>?,
        avoidTolls: Boolean
    ): Result<DirectionsResponseDto> = withContext(Dispatchers.IO) {
        try {
            val waypointsParam = waypoints?.takeIf { it.isNotEmpty() }?.joinToString("|")
            val avoidParam = if (avoidTolls) "tolls" else null

            val response = apiService.getDirections(
                origin = origin,
                destination = destination,
                waypoints = waypointsParam,
                avoid = avoidParam
            )

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to compute route: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
