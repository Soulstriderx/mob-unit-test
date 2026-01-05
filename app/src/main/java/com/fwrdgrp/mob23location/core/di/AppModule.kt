package com.fwrdgrp.mob23location.core.di

import com.fwrdgrp.mob23location.data.repo.Repo
import com.fwrdgrp.mob23location.data.repo.RepoImpl
import com.fwrdgrp.mob23location.data.repo.RepoImplTest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun providesRepo(): Repo {
        return RepoImpl()
    }
}