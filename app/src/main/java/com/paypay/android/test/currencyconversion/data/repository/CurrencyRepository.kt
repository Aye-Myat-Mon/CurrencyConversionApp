package com.paypay.android.test.currencyconversion.data.repository

import androidx.lifecycle.liveData
import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import com.paypay.android.test.currencyconversion.data.services.CurrencyService
import com.paypay.android.test.currencyconversion.model.CurrencyModel
import com.paypay.android.test.currencyconversion.utils.Result
import com.paypay.android.test.currencyconversion.utils.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * Created by ayemyatmon on 04,January,2022
 */
class CurrencyRepository @Inject constructor(
    private val currencyService: CurrencyService,
    private val currencyDao: CurrencyDao) {

    suspend fun getCurrency(accessKey: String, source: String) : Flow<Result<CurrencyModel>?> {
        return flow {
            emit(fetchCurrencyCached())
            emit(Result.Loading)

            val response = safeApiCall {
                currencyService.getCurrencyList(accessKey, source)
            }

            when(response) {
                is Result.Success -> {
                    emit(Result.Success(response.data))
                    currencyDao.deleteAll(response.data)
                    currencyDao.insertAll(response.data)
                }
                is Result.Error -> {
                    emit(Result.Error(response.exception))
                }
                else -> {
                    emit(Result.Empty)
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun fetchCurrencyCached(): Result<CurrencyModel>? =
        currencyDao.getAll()?.let {
            Result.Success(it)
        }
}