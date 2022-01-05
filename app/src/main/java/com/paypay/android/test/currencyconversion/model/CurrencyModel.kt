package com.paypay.android.test.currencyconversion.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Entity(tableName = "currency_table")
data class CurrencyModel(
    @NonNull
    @PrimaryKey
    val source: String,
    val success:Boolean,
    val quotes: Quote
)

data class Quote(
    val USDAED: String,
    val USDAFN: String,
    val USDALL: String
)
