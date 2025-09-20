package com.app.schoolApp.domain.usecases

import com.app.schoolApp.data.model.SatScoreDto
import com.app.schoolApp.data.state.Resource
import com.app.schoolApp.domain.repostory.SchoolsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetSatScoreUseCase @Inject constructor(
    private val repository: SchoolsRepository
) {
    operator fun invoke(): Flow<Resource<List<SatScoreDto>>> = repository.getSatScoreForSchool()
}