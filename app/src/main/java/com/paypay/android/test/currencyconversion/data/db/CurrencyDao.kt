package com.paypay.android.test.currencyconversion.data.db

import androidx.room.*
import com.paypay.android.test.currencyconversion.model.CurrencyModel

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currency_table")
    fun getAll(): CurrencyModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(quote: CurrencyModel)

    @Delete
    fun deleteAll(quote: CurrencyModel)
}