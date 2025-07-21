package com.app.githubrepotask.data.repository

import com.app.githubrepotask.data.local.dao.RepoDao
import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.data.model.Results
import com.app.githubrepotask.data.remote.GitHubApiService
import com.app.githubrepotask.domain.repostory.GitHubRepository
import javax.inject.Inject

class GitHubRepositoryImpl @Inject constructor(
    private val apiService: GitHubApiService,
    private val repoDao: RepoDao
) : GitHubRepository {

    override suspend fun getUserRepos(username: String): Results<List<RepoEntity>> {
        return runCatching {
            val response = apiService.getRepos(username)

            if (response.isSuccessful) {
                val remoteRepos = response.body().orEmpty().map { it.toEntity(username) }

                // Replace cache with fresh data
                repoDao.deleteReposByUsername(username)
                repoDao.insertRepos(remoteRepos)

                Results.Success(remoteRepos)
            } else {
                fallbackToCacheOrError(
                    username,
                    "API Error ${response.code()}: ${response.message()}"
                )
            }
        }.getOrElse { exception ->
            fallbackToCacheOrError(
                username = username,
                errorMessage = "Network Error: ${exception.localizedMessage}",
                exception = exception
            )
        }
    }

    private suspend fun fallbackToCacheOrError(
        username: String,
        errorMessage: String,
        exception: Throwable? = null
    ): Results<List<RepoEntity>> {
        val cachedRepos = repoDao.getReposByUsername(username)
        return if (cachedRepos.isNotEmpty()) {
            Results.Success(cachedRepos)
        } else {
            Results.Error(errorMessage, exception)
        }
    }
}
