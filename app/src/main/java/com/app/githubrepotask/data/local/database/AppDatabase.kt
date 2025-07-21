package com.app.githubrepotask.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.githubrepotask.data.local.dao.RepoDao
import com.app.githubrepotask.data.local.entities.RepoEntity
import com.app.githubrepotask.data.local.dao.SearchHistoryDao
import com.app.githubrepotask.data.local.entities.SearchHistoryEntity

@Database(
    entities = [RepoEntity::class, SearchHistoryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
    abstract fun searchHistoryDao(): SearchHistoryDao
}
