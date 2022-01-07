package com.paypay.android.test.currencyconversion.ui

import androidx.lifecycle.*
import com.paypay.android.test.currencyconversion.Constants
import com.paypay.android.test.currencyconversion.data.repository.CurrencyRepository
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.utils.Result
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
    private val currencyListResultMediator = MediatorLiveData<Result<CurrencyListModel>>()

    val currencyLiveResult: LiveData<CurrencyModel> = Transformations.map(currencyResultMediator) {
        (it as? Result.Success)?.data
    }

    val currencyListResult: LiveData<CurrencyListModel> =
        Transformations.map(currencyListResultMediator) {
            (it as? Result.Success)?.data
        }

    fun getLiveCurrency(source: String) {
        viewModelScope.launch {
            currencyRepository.getLiveCurrency(Constants.API_KEY, source).collect {
                Timber.d("Currency Result $it")
                currencyResultMediator.value = it
            }
        }

    }

    fun getListCurrency() {
        viewModelScope.launch {
            currencyRepository.getListCurrency(Constants.API_KEY).collect {
                Timber.d("Currency List Result $it")
                currencyListResultMediator.value = it
            }
        }

    }
}