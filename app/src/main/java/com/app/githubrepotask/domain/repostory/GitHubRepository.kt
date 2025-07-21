package com.app.githubrepotask.domain.repostory

import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.data.model.Results


interface GitHubRepository {
    suspend fun getUserRepos(username: String): Results<List<RepoEntity>>
}
