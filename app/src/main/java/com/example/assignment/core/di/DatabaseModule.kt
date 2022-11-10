package com.example.assignment.core.di

import android.content.Context
import androidx.room.Room
import com.example.assignment.core.data_base.AppDatabase
import com.example.assignment.core.data_base.dao.PropertyEntityDao
import com.example.assignment.feature.add_marker.data.repo_impl.PropertyRepoImpl
import com.example.assignment.feature.add_marker.domain.repo.IPropertyRepo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): PropertyEntityDao {
        return appDatabase.channelDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "RssReader"
        ).build()
    }

    @Provides
    @Singleton
    fun providePropertyRepo(dao: PropertyEntityDao): IPropertyRepo {
        return PropertyRepoImpl(dao)
    }
}

@Module
@InstallIn(ActivityComponent::class)
abstract class RepoModel {

    @Binds
    abstract fun bindPropertyRepo(
        analyticsServiceImpl: PropertyRepoImpl
    ): IPropertyRepo
}