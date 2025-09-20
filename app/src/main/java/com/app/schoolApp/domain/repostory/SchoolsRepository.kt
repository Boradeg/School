package com.app.schoolApp.domain.repostory

import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.data.model.SchoolDto
import com.app.schoolApp.data.state.Resource
import kotlinx.coroutines.flow.Flow

interface SchoolsRepository {

    fun getSchools(): Flow<Resource<List<SchoolDto>>>
    fun getSatScoreForSchool(): Flow<Resource<List<SatScoreDto>>>


}