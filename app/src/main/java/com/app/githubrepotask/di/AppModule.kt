package com.app.githubrepotask.di

import android.content.Context
import androidx.room.Room
import com.app.githubrepotask.data.local.database.AppDatabase
import com.app.githubrepotask.data.local.dao.RepoDao
import com.app.githubrepotask.data.local.dao.SearchHistoryDao
import com.app.githubrepotask.data.remote.GitHubApiService
import com.app.githubrepotask.data.repository.GitHubRepositoryImpl
import com.app.githubrepotask.data.repository.SearchHistoryRepositoryImpl
import com.app.githubrepotask.domain.repostory.GitHubRepository
import com.app.githubrepotask.domain.repostory.SearchHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "github_repo_db"
        ).build()
    }

    @Provides
    fun provideRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }

    @Provides
    fun provideSearchHistoryDao(appDatabase: AppDatabase): SearchHistoryDao {
        return appDatabase.searchHistoryDao()
    }

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGitHubRepository(
        apiService: GitHubApiService,
        repoDao: RepoDao,
    ): GitHubRepository {
        return GitHubRepositoryImpl(apiService, repoDao)
    }
    @Provides
    fun provideSearchHistoryRepository(dao: SearchHistoryDao): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(dao)
    }
}



