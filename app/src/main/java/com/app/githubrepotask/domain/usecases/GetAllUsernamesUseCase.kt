package com.app.githubrepotask.domain.usecases

import com.app.githubrepotask.data.model.Results
import com.app.githubrepotask.domain.repostory.SearchHistoryRepository
import javax.inject.Inject

class GetAllUsernamesUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(): Results<List<String>> {
        return repository.getAllUsernames()
    }
}