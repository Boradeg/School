package com.app.githubrepotask.domain.repostory

import com.app.githubrepotask.data.model.Results

interface SearchHistoryRepository {
    suspend fun getAllUsernames(): Results<List<String>>
    suspend fun insertUsername(username: String)
}