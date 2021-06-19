package com.omaraly.currency.di.module

import com.omaraly.currency.db.RateDao
import com.omaraly.currency.remote.api.RatesApi
import com.omaraly.currency.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        dataDao: RateDao,
        dataApi: RatesApi
    ): MainRepository {
        return MainRepository(dataDao, dataApi)
    }

}
