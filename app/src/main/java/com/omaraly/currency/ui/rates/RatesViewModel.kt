package com.omaraly.currency.ui.rates

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaraly.currency.db.entity.RateList
import com.omaraly.currency.model.Resource
import com.omaraly.currency.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


@ExperimentalCoroutinesApi
class RatesViewModel
@ViewModelInject
constructor(
    private var rateRepository: MainRepository
) :
    ViewModel() {

    private val _currencyResponse = MutableLiveData<Resource<RateList>>()
    val currencyResponse: LiveData<Resource<RateList>> = _currencyResponse

    fun getRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = rateRepository.getRates()
            withContext(Dispatchers.Main) {
                _currencyResponse.postValue(handleResponse(response))
            }
        }
    }

    private fun handleResponse(response: Response<RateList?>): Resource<RateList> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
         return Resource.Error(response.message())
    }
}
