package com.paypay.android.test.currencyconversion.ui

import androidx.lifecycle.*
import com.paypay.android.test.currencyconversion.Constants
import com.paypay.android.test.currencyconversion.utils.Result
import com.paypay.android.test.currencyconversion.data.repository.CurrencyRepository
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by ayemyatmon on 05,January,2022
 */
@HiltViewModel
class MainViewModel @Inject constructor(private val currencyRepository: CurrencyRepository): ViewModel() {

    private val currencyResultMediator = MediatorLiveData<Result<CurrencyModel>>()

    val currencyResult: LiveData<CurrencyModel> = Transformations.map(currencyResultMediator) {
        (it as? Result.Success)?.data
    }

    val currencyResponseError: LiveData<String> = Transformations.map(currencyResultMediator) {
        (it as? Result.Error)?.exception?.localizedMessage
    }

    fun getCurrency(source: String) {
        viewModelScope.launch {
            currencyRepository.getCurrency(Constants.API_KEY, source).collect {
                Timber.d("Currency Result $it")
                currencyResultMediator.value = it
            }
        }

    }
}