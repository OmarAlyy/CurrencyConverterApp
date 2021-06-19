package com.omaraly.currency.repository

import com.omaraly.currency.db.RateDao
import com.omaraly.currency.db.entity.RateList
import com.omaraly.currency.remote.api.RatesApi
import retrofit2.Response

class MainRepository
constructor(
    private val ratesLocalDataSource: RateDao,
    private val ratesRemoteDataSource: RatesApi,
) {

    suspend fun getRates(): Response<RateList?> {
        try {
            updateRatesFromRemoteDataSource()
        } catch (e: Exception) {
        }
        return getSavedRates()

    }

    private fun getSavedRates(): Response<RateList?> {
        return Response.success(ratesLocalDataSource.getRates())
    }

    private suspend fun updateRatesFromRemoteDataSource() {
        val response = ratesRemoteDataSource.getRates()
        if (response.isSuccessful)
            response.body()?.let { it1 -> ratesLocalDataSource.insertOrUpdateRates(it1) }
    }

}