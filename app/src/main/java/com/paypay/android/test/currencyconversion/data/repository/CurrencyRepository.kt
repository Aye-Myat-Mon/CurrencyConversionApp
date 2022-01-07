package com.paypay.android.test.currencyconversion.data.repository

import com.paypay.android.test.currencyconversion.data.db.CurrencyDao
import com.paypay.android.test.currencyconversion.data.services.CurrencyService
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
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

    suspend fun getLiveCurrency(accessKey: String, source: String): Flow<Result<CurrencyModel>?> {
        return flow {
            emit(fetchCurrencyCached())
            emit(Result.Loading)

            val response = safeApiCall {
                currencyService.getLiveCurrency(accessKey, source)
            }

            when (response) {
                is Result.Success -> {
                    if (response.data.success) {
                        currencyDao.deleteLive(response.data)
                        currencyDao.insertLive(response.data)
                        emit(Result.Success(response.data))
                    }
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

    suspend fun getListCurrency(accessKey: String): Flow<Result<CurrencyListModel>?> {
        return flow {
            emit(fetchCurrencyListCached())
            emit(Result.Loading)

            val response = safeApiCall {
                currencyService.getListCurrency(accessKey)
            }

            when (response) {
                is Result.Success -> {
                    if (response.data.success) {
                        currencyDao.deleteList(response.data)
                        currencyDao.insertList(response.data)
                        emit(Result.Success(response.data))
                    }
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

    private fun fetchCurrencyCached(): Result<CurrencyModel> =
        currencyDao.getLiveCurrency().let {
            Result.Success(it)
        }

    private fun fetchCurrencyListCached(): Result<CurrencyListModel> =
        currencyDao.getListCurrency().let {
            Result.Success(it)
        }
}