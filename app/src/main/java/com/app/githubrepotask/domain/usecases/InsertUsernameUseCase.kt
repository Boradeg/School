package com.app.githubrepotask.domain.usecases

import com.app.githubrepotask.domain.repostory.SearchHistoryRepository
import javax.inject.Inject

class InsertUsernameUseCase @Inject constructor(
    private val repository: SearchHistoryRepository
) {
    suspend operator fun invoke(username: String) {
        repository.insertUsername(username)
    }
}