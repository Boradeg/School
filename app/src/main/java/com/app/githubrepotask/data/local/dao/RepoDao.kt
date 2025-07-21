package com.app.githubrepotask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.githubrepotask.data.local.entities.RepoEntity


@Dao
interface RepoDao {

    @Query("DELETE FROM repos WHERE username = :username")
    suspend fun deleteReposByUsername(username: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<RepoEntity>)

    @Query("SELECT * FROM repos WHERE username = :username")
    suspend fun getReposByUsername(username: String): List<RepoEntity>
}
