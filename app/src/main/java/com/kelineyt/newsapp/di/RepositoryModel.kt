package com.kelineyt.newsapp.di

import com.kelineyt.newsapp.data.repositoryIpml.NewsRepositoryIpml
import com.kelineyt.newsapp.data.web.NewApi
import com.kelineyt.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

class RepositoryModel {
    @Singleton
    @Provides
    fun provideRepository(api: NewApi) : NewsRepository {
        return NewsRepositoryIpml(api);
    }
}