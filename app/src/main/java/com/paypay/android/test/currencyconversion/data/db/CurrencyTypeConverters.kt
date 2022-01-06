package com.paypay.android.test.currencyconversion.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonObject

/**
 * Created by ayemyatmon on 05,January,2022
 */
class CurrencyTypeConverters {
    @TypeConverter
    fun appToString(quote: JsonObject): String = Gson().toJson(quote)

    @TypeConverter
    fun stringToApp(string: String): JsonObject = Gson().fromJson(string, JsonObject::class.java)
}