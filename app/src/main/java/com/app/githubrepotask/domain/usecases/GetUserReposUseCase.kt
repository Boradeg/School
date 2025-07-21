package com.app.githubrepotask.domain.usecases

import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.data.model.Results
import com.app.githubrepotask.domain.repostory.GitHubRepository
import javax.inject.Inject

class GetUserReposUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    suspend operator fun invoke(username: String): Results<List<RepoEntity>> {
        return repository.getUserRepos(username)
    }
}




