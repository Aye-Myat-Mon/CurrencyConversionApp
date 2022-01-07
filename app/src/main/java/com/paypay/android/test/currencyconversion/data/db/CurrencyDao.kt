package com.paypay.android.test.currencyconversion.data.db

import androidx.room.*
import com.paypay.android.test.currencyconversion.model.CurrencyListModel
import com.paypay.android.test.currencyconversion.model.CurrencyModel

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM all_currency_table")
    fun getListCurrency(): CurrencyListModel

    @Query("SELECT * FROM live_currency_table")
    fun getLiveCurrency(): CurrencyModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(quote: CurrencyListModel)

    @Delete
    fun deleteList(quote: CurrencyListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLive(quote: CurrencyModel)

    @Delete
    fun deleteLive(quote: CurrencyModel)
}