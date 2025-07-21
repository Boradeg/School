package com.app.githubrepotask.data.repository

import com.app.githubrepotask.data.local.dao.SearchHistoryDao
import com.app.githubrepotask.data.local.entities.SearchHistoryEntity
import com.app.githubrepotask.data.model.Results
import com.app.githubrepotask.domain.repostory.SearchHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchHistoryRepositoryImpl @Inject constructor(
    private val dao: SearchHistoryDao
) : SearchHistoryRepository {

    /**
     * Retrieves the list of previously searched usernames.
     */
    override suspend fun getAllUsernames(): Results<List<String>> = withContext(Dispatchers.IO) {
        return@withContext try {
            val usernames = dao.getSearchHistory()
                .first()
                .map { it.username }

            Results.Success(usernames)
        } catch (e: Exception) {
            Results.Error("Failed to load search history", e)
        }
    }

    /**
     * Inserts a new username into search history if it's not blank.
     */
    override suspend fun insertUsername(username: String) = withContext(Dispatchers.IO) {
        val trimmed = username.trim()
        if (trimmed.isNotEmpty()) {
            dao.deleteUsername(username)
            dao.insertUsername(SearchHistoryEntity(username = username))
        }

    }
}
