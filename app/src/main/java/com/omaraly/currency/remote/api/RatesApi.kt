package com.omaraly.currency.remote.api

import com.omaraly.currency.db.entity.RateList
import com.omaraly.currency.app.Constants.ACCESS_KEY
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Response
import retrofit2.http.GET


@ExperimentalCoroutinesApi
interface RatesApi {


    @GET("latest?access_key=$ACCESS_KEY")
    suspend fun getRates(): Response<RateList>


}
