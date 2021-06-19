package com.omaraly.currency.di.module

import android.content.Context
import androidx.room.Room
import com.omaraly.currency.db.AppDatabase
import com.omaraly.currency.db.RateDao
import com.omaraly.currency.app.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBlogDAO(blogDatabase: AppDatabase): RateDao {
        return blogDatabase.rateDao()
    }
}