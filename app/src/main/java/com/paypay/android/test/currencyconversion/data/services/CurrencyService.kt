package com.paypay.android.test.currencyconversion.data.services

import com.paypay.android.test.currencyconversion.model.CurrencyModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ayemyatmon on 04,January,2022
 */
interface CurrencyService {
    @GET("live")
    suspend fun getCurrencyList(
        @Query("access_key") access_key: String,
        @Query("source") source: String
    ): Response<CurrencyModel>
}