package com.app.schoolApp.data.remote

import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.data.model.SchoolDto
import retrofit2.http.GET

interface SchoolsApi {
    @GET("resource/s3k6-pzi2.json")
    suspend fun getSchools(): List<SchoolDto>

    @GET("resource/f9bf-2cp4.json")
    suspend fun getSatScores(): List<SatScoreDto>
}
