package com.aldemir.listadapter.di

import com.aldemir.listadapter.data.AppDatabase
import com.aldemir.listadapter.features.main.repositories.TaskRepository
import com.aldemir.listadapter.features.main.repositories.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryTaskModule {
    @Singleton
    @Provides
    fun provideRepository(db: AppDatabase): TaskRepository = TaskRepositoryImpl(db.taskDao())
}