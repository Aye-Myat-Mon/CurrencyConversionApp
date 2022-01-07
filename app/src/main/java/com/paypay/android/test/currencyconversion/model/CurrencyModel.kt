package com.paypay.android.test.currencyconversion.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.JsonObject

/**
 * Created by ayemyatmon on 04,January,2022
 */
@Entity(tableName = "live_currency_table")
data class CurrencyModel(
    @NonNull
    @PrimaryKey
    val source: String,
    val success: Boolean,
    val quotes: JsonObject
)

@Entity(tableName = "all_currency_table")
data class CurrencyListModel(
    @NonNull
    @PrimaryKey
    val success: Boolean,
    val currencies: JsonObject
)

data class Quote(
    val code: String,
    val value: Double
)
