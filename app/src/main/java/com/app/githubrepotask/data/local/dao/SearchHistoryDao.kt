package com.app.githubrepotask.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.app.githubrepotask.data.local.entities.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsername(entity: SearchHistoryEntity)



    @Query("DELETE FROM search_history WHERE username = :username")
    suspend fun deleteUsername(username: String)

    @Query("SELECT * FROM search_history ORDER BY id DESC")
     fun getSearchHistory(): Flow<List<SearchHistoryEntity>>
}