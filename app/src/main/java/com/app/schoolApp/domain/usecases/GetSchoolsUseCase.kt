package com.app.schoolApp.domain.usecases

import com.app.schoolApp.data.model.SchoolDto
import com.app.schoolApp.domain.repostory.SchoolsRepository
import com.app.schoolApp.data.state.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSchoolsUseCase @Inject constructor(
    private val repository: SchoolsRepository
) {
    operator fun invoke(): Flow<Resource<List<SchoolDto>>> {
        return repository.getSchools()
    }
}



