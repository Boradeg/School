package com.app.schoolApp.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.data.model.SchoolDto
import com.app.schoolApp.data.remote.SchoolsApi
import com.app.schoolApp.domain.repostory.SchoolsRepository
import com.app.schoolApp.data.state.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class SchoolsRepositoryImpl @Inject constructor(
    private val api: SchoolsApi
) : SchoolsRepository {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getSchools(): Flow<Resource<List<SchoolDto>>> = flow {
        emit(Resource.Loading())
        try {
            val schools = api.getSchools()
            emit(Resource.Success(schools))
        } catch (e: IOException) {
            // No internet or network failure
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: HttpException) {
            // Retrofit HTTP error (4xx, 5xx)
            emit(Resource.Error("Server error: ${e.message}"))
        } catch (e: Exception) {
            // Anything else
            emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)



    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun getSatScoreForSchool(): Flow<Resource<List<SatScoreDto>>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getSatScores()
            emit(Resource.Success(response)) // directly emit DTO list
        } catch (e: IOException) {
            // No internet or network failure
            emit(Resource.Error("No internet connection. Please check your network."))
        } catch (e: HttpException) {
            // Retrofit HTTP error (4xx, 5xx)
            emit(Resource.Error("Server error: ${e.message}"))
        } catch (e: Exception) {
            // Anything else
            emit(Resource.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)

}



