package com.app.schoolApp.di

import com.app.schoolApp.data.remote.SchoolsApi
import com.app.schoolApp.data.repository.SchoolsRepositoryImpl
import com.app.schoolApp.domain.repostory.SchoolsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://data.cityofnewyork.us/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideSchoolsApi(retrofit: Retrofit): SchoolsApi =
        retrofit.create(SchoolsApi::class.java)

    @Provides
    @Singleton
    fun provideSchoolsRepository(api: SchoolsApi): SchoolsRepository =
        SchoolsRepositoryImpl(api)
}



